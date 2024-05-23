from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import re

# clean function
def clean_text(text):

    cleaned_text = re.sub(r'[^\w\s]', '', text)
    cleaned_text = re.sub(r'\s+', ' ', cleaned_text)
    return cleaned_text.strip()


def getInkLevels3(url):
    try:
        path_driver = "D:/chromedriver-win64/chromedriver-win64/chromedriver.exe"

        # Configuration du service pour ChromeDriver
        service = Service(path_driver)
        options = Options()
       

        # Initialiser le driver Chrome
        driver = webdriver.Chrome(service=service, options=options)

        # Accéder au lien sur le web
        driver.get(url)

        # Attendre jusqu'à ce que le bouton de connexion soit présent
        wait = WebDriverWait(driver, 10)
        login_button = wait.until(EC.presence_of_element_located((By.ID, 'submitButton'))) 
        login_button.click()

        # Attendre le chargement de la page après la connexion
        time.sleep(10)

        # Récupérer le contenu de la page après la connexion avec Selenium
        pageSource = driver.page_source

        driver.quit()

        soup = BeautifulSoup(pageSource, 'html.parser')

        # Dictionnaire pour stocker les niveaux d'encre
        inkLevels = {}

        # Trouver la table avec les niveaux d'encre
        divPage = soup.find('table', class_="ItemListComponent")
        tbody = divPage.find('tbody')

        # Parcourir toutes les lignes de la table
        rows = tbody.find_all('tr')

        for row in rows:
            percent_td = row.find_all('td')[1]
            
            if 'class' not in percent_td.attrs or not percent_td['class']:
                percent_text = percent_td.text.strip()
                percent = int(clean_text(percent_text))
                inkLevels['noir'] = percent


        return inkLevels

    except Exception as e:
        print("Erreur:", e)
        return {}




