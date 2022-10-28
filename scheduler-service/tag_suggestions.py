import openai
import json
import random
# If it doesn't work, open "Install Certificates.command"
# import nltk
# nltk.download('wordnet')
# nltk.download('omw-1.4')
from nltk.corpus import wordnet
from nltk.tokenize import word_tokenize
#import nltk
#nltk.download('punkt')
from collections import defaultdict
import pprint

#Get API Key from Open AI
openai.api_key = API_KEY


#Find Synonyms of each word of Input String
def text_parser_synonym_finder(text: str):
    tokens = word_tokenize(text)
    synonyms = defaultdict(list)

    for token in tokens:
        for syn in wordnet.synsets(token):
            for i in syn.lemmas():
                synonyms[token].append(i.name())
    #pprint.pprint(dict(synonyms)) #print all synonyms
    #synonym_output = pprint.pformat((dict(synonyms)))
    synonym_output = dict(synonyms)

    return synonym_output

def find_tags(textBody):
    #Keywords Parameters
    response = openai.Completion.create(
      model="text-davinci-002",
      prompt="Extract keywords from this text:\n\n" + textBody,
      temperature=0.3,
      max_tokens=60,
      top_p=1,
      frequency_penalty=0.8,
      presence_penalty=0
    )

    #Find all Keywords/Phrases
    keywords = response["choices"][0]["text"].lower()
    index = keywords.index("\n\n")
    keywords = keywords[index+2:]

    #print(keywords)

    #Move Keywords into Array
    keywords = keywords.replace(',', '')
    keywordsArray = keywords.split()

    #print(keywordsArray)

    #Run Function to find synonyms with keywords
    synonymsDictionary = text_parser_synonym_finder(text=keywords)
    #print(synonymsDictionary)


    #print(keywordsArray)

    #x is keyword; add 2 synonyms from each word
    shortlistSynonyms = []

    for x in keywordsArray:
        #Remove Duplicate Synonyms
        allSynonyms = list(dict.fromkeys(synonymsDictionary[x]))

        #Remove Key Word is Present
        if x in allSynonyms:
            allSynonyms.remove(x)

        #Get 2 random synonyms if 2 or more synonyms present, get 1 synonym if 1 present, leave alone if 0 synonyms
        if len(allSynonyms) >= 2:
            firstRandom = random.randint(0, len(allSynonyms) - 1)
            secondRandom = firstRandom
            while secondRandom == firstRandom:
                secondRandom = random.randint(0, len(allSynonyms) - 1)
            shortlistSynonyms.append(allSynonyms[firstRandom])
            shortlistSynonyms.append(allSynonyms[secondRandom])

        elif len(allSynonyms) >= 1:
            shortlistSynonyms.append(allSynonyms[0])

        #print(allSynonyms)

    #print(shortlistSynonyms)

    allKeywordsAndRelated = []

    for x in keywordsArray:
        allKeywordsAndRelated.append(x)

    for x in shortlistSynonyms:
        allKeywordsAndRelated.append(x)

    return allKeywordsAndRelated

#Input Text
textBody = "Computers are amazing machines. They have a keyboard, mouse, and screen. They are used to revolutionize lives and are very important in this world."

print(find_tags(textBody))