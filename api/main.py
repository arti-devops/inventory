from typing import Union
from fastapi import FastAPI
from printer import getAll
from status import getStatus

app = FastAPI()


# route pour excecuter toutes les fonctions
@app.get("/printers")
def printerUse(cat : int, url:str):
    result = getAll(cat, url)
    return result

@app.get('/devices/status', tags=["Device Status"])
def statusIp(ip):
    return getStatus(ip)

