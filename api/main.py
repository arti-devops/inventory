from typing import Union
from fastapi import FastAPI

# from status import getStatus
from printer import getAll
from ipp import getPrinterDetails

app = FastAPI()


# route pour excecuter toutes les fonctions
@app.get("/printers")
def printerUse(cat : int, url:str):
    result = getAll(cat, url)
    return result

@app.get("/printers")
async def printerDetails(ip: str):
    return await getPrinterDetails(ip)

@app.get('/devices/status', tags=["Device Status"])
def statusIp(ip):
    return getStatus(ip)

