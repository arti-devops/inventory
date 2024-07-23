from pyipp import IPP, Printer

ction = {}

def addColorLevel(marker, color, ction):
    if color in marker.name:
        ction[color.lower()] = marker.level

async def fetchPrinter(ip: str) -> dict:
    async with IPP("ipp://" + ip, request_timeout=1) as ipp:
        printer: Printer = await ipp.printer()
        for marker in printer.markers :
            for color in ["Black","Cyan","Magenta","Yellow"]:
                addColorLevel(marker, color, ction)
        return {
            "status":1,
            "url": printer.info.more_info,
            "color": ction
        }

async def getPrinterDetails(ip: str) -> dict:
    try:
        return await fetchPrinter(ip)
    except :
        return {
            "status":0,
            "url": "",
            "color": {
                "black":0,
                "magenta":0,
                "cyan":0,
                "yellow":0
            }
        }