def symmatric2(string):
    if (len(string) >= 2):
        if (string[0:2] == (string[-2:])):
            return True
    return False

print(symmatric2("lollo"))