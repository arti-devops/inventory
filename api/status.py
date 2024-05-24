import requests


# get status
def ipStatus(ip):
    
    try:
        response = requests.get(f'http://{ip}', timeout= 0.1)
        
        if response.status_code == 200:
            return 1
        else:
            return 0
    except requests.RequestException as e:
        print('erreur:', e)
        
        return 0
    
def getStatus(ip):
    status = ipStatus(ip)
    data = {
        "status": status
    }

    return data