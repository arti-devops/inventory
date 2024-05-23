from bs4 import BeautifulSoup
import re
import requests
from pythonping import ping
from hp import getInkLevels2
from canon import getInkLevels3
 
 

# get printer status
def getStatus(url):
    try:
        response = requests.get(url)
        if response.status_code == 200:
            return 1
        else:
            return 0
    except Exception as e:
        print("Erreur de l'imprimante:", e)
        return 0


# fonction de suppression des caracteres speciaux et espaces supplementaires
def clean_text(text):

    cleaned_text = re.sub(r'[^\w\s]', '', text)
    cleaned_text = re.sub(r'\s+', ' ', cleaned_text)
    return cleaned_text.strip()

# la fonction pour avoir le niveau des encres niveau 1
def getInkLevels1(url):
    try:
        response = requests.get(url)
        response.raise_for_status()

        soup = BeautifulSoup(response.text, 'html.parser')

        ink_levels = {}

        cartridge_names = ["black", "cyan", "red", "yellow"]

        # Recherche de la zone principale du contenu
        main_content = soup.find('table', class_='mainContentArea')

        if main_content:
            # Parcours des éléments <td> à l'intérieur
            for id, td in enumerate(main_content.find_all('td', class_='width25')):
                ink_level_td = td.find('td', class_='alignRight valignTop')
                if ink_level_td:
                    ink_level_text = clean_text(ink_level_td.get_text(strip=True))
                    ink_level = int(ink_level_text) if ink_level_text else 0

                    
                    ink_levels[cartridge_names[id]] = ink_level

        return ink_levels
    except Exception as e:
        print("Erreur de récupération :", e)
        return {}



# function that commpose other functions
def getAll(cat, url):

    fakeColor = {
        'black': 0,
        'cyan':0,
        'red':0,
        'yellow':0
    }
    status= getStatus(url)

    if cat == 1:
        inkLevels = getInkLevels1(url)
        print(inkLevels)
    else:
        inkLevels = fakeColor




    

    if inkLevels:
        data = {
                'status': status,
                'url': url,
                'color': inkLevels,
                
            }
    else:
        data = {
            'status': status,
            "url": url,
            "color": fakeColor
        }

    return data

