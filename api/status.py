from pythonping import ping


# get status
def ipStatus(ip):
    try:
        response = ping(ip, count=1, timeout=0.1)
        if response.success():
            r = 1
            print(r)
            return r
        else:
            r = 0
            print(r)
            return r
    except Exception as e:
        print('erreur:', e)
        r = 0
        print(r)
        return r
    
def getStatus(ip):
    status = ipStatus(ip)
    data = {
        "status": status
    }

    return data