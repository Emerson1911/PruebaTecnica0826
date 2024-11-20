from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm
from pydantic import BaseModel
from typing import Union
from passlib.context import CryptContext
from datetime import timedelta, datetime
from jose import JWTError, jwt

fake_users_db = {
    "johndoe": {
        "username": "johndoe",
        "full_name": "John Doe",
        "email": "johndoe@example.com",
        "hashed_password": "$2b$12$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW",
        "disabled": False,
    }
}


app = FastAPI()

OAuth2_scheme = OAuth2PasswordBearer("/token")

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

SECRET_KEY = "d933a6501c9ea416728b9c01f9c2ffd435ccbe077f030526fd95ce4128edd"
ALGORITHM = "HS256"


class User(BaseModel):
    username: str
    full_name: Union[str, None] = None
    email: Union[str, None] = None
    disabled: Union[bool, None] = None


class UserInDB(User):
    hashed_password: str


def get_user(db, username):
    if username in db:
        user_data = db[username]
        return UserInDB(**user_data)
    return []


def verify_password(plain_password, hashed_password):
    return pwd_context.verify(plain_password, hashed_password)


def authenticate_user(db, username, password):
    user = get_user(db, username)
    if not user:
        raise HTTPException(
            status_code=401,
            detail="Error de autenticación",
            headers={"WWW-Authenticate": "Bearer"},
        )
    if not verify_password(
        password, user.hashed_password
    ):  # Changed here to use `user.hashed_password`
        raise HTTPException(
            status_code=401,
            detail="Error de autenticación",
            headers={"WWW-Authenticate": "Bearer"},
        )
    return user


def create_token(data: dict, time_expired: Union[datetime, None] = None):
    data_copy = data.copy()
    if time_expired is None:
        expires = datetime.utcnow() + timedelta(minutes=15)
    else:
        expires = datetime.utcnow() + time_expired
    data_copy.update({"exp": expires})
    token_jwt = jwt.encode(data_copy, key=SECRET_KEY, algorithm=ALGORITHM)
    print(token_jwt)
    return token_jwt


def get_user_current(token: str = Depends(OAuth2_scheme)):
    try:
        token_decode = jwt.decode(token, key=SECRET_KEY, algorithms=[ALGORITHM])
        username = token_decode.get("sub")
        if username == None:
            raise HTTPException(
                status_code=401,
                detail="Error de autenticación",
                headers={"WWW-Authenticate": "Bearer"},
            )
    except JWTError:
        raise HTTPException(
            status_code=401,
            detail="Error de autenticación",
            headers={"WWW-Authenticate": "Bearer"},
        )
    user = get_user(fake_users_db, username)
    if not user:
        raise HTTPException(
            status_code=401,
            detail="Error de autenticación",
            headers={"WWW-Authenticate": "Bearer"},
        )
    return user

def get_user_desable_current(user: User = Depends(get_user_current)):
    if user.disabled:
        raise HTTPException(status_code=400, detail="Usuario deshabilitado")
    return user
    

@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/users/me")
async def user(user: User = Depends(get_user_desable_current)):
    return user


@app.post("/token")
async def token(form_data: OAuth2PasswordRequestForm = Depends()):
    user = authenticate_user(fake_users_db, form_data.username, form_data.password)
    access_token_expires = timedelta(minutes=30)
    access_token_jwt = create_token({"sub": user.username}, access_token_expires)
    print(access_token_expires)
    return {"access_token": access_token_jwt, "token_type": "bearer"}
