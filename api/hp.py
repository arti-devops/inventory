from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
import time
import re

# Fonction de nettoyage du texte pour extraire les chiffres
def clean_text(text):
    cleaned_text = re.sub(r'[^\d]', '', text)
    return cleaned_text

# function getting ink levels
def getInkLevels2(url):
    try:
        # Chemin vers le chromedriver
        driver_path = "D:/chromedriver-win64/chromedriver-win64/chromedriver.exe"

        service = Service(driver_path)
        options = Options()
        options.add_argument("--headless")
        

        # Initialisation du driver Chrome
        driver = webdriver.Chrome(service=service, options=options)

        # Navigation vers l'URL
        driver.get(url)

        # Attendre que la page soit complètement chargée
        time.sleep(20)

        # Récupération du contenu de la page avec Selenium
        page_source = driver.page_source

        # Fermeture du navigateur
        driver.quit()

        # Analyse du contenu de la page avec BeautifulSoup
        soup = BeautifulSoup(page_source, 'html.parser')

        # Listes pour stocker les valeurs extraites
        pages_imprimees = [None] * 4
        pages_restantes = [None] * 4
        cartridge_names = ["noir", "cyan", "magenta", "jaune"]
        ink_levels = {}

        # Vérification de la présence des éléments par classe
        rows = soup.find_all('tr')
        if rows:
            for row in rows:
                # Récupérer toutes les cellules de la ligne
                cells = row.find_all('td')
                if cells:
                    first_cell_text = cells[0].text.strip()
                    if "Pages imprimées avec ce consommable" in first_cell_text:
                        # Extraire les chiffres des cellules correspondantes
                        for i, cell in enumerate(cells[1:]):
                            number = clean_text(cell.text.strip())
                            if number:
                                pages_imprimees[i] = int(number)
                    elif "Pages restantes à imprimer (estimation)" in first_cell_text:
                        # Extraire les chiffres des cellules correspondantes
                        for i, cell in enumerate(cells[1:]):
                            number = clean_text(cell.text.strip())
                            if number:
                                pages_restantes[i] = int(number)
        else:
            print("Le conteneur principal n'a pas été trouvé.")

        # Calculer les pourcentages
        for i in range(4):
            imprimes = pages_imprimees[i]
            restants = pages_restantes[i]
            if imprimes is not None and restants is not None:
                total_pages = imprimes + restants
                if total_pages > 0:
                    pourcentage = (restants / total_pages) * 100
                    ink_levels[cartridge_names[i]] = round(pourcentage, 2) 
                else:
                    ink_levels[cartridge_names[i]] = 0.00  
            
        print(ink_levels)
        return ink_levels
    

    except Exception as e:
        print("Erreur:", e)
        return {}

# getInkLevels2("http://192.168.5.1/#hId-pgConsumables")