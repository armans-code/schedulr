from fastapi import FastAPI
from ml_tag_generator import *

app = FastAPI()

@app.get("/root")
async def root():
    return "Welcome to the ML Service!"

@app.get("/find-tags")
async def find_tags(caption: str):
    tags = generate_tags(caption)
    print(tags)
    return {"tags": tags}
