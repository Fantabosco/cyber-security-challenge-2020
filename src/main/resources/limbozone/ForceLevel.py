import py7zr

def tryOpen(password):
	try:
		with py7zr.SevenZipFile('level_1.7z', mode='r', password=password) as z:
			z.extractall(path="result")
		exit()
	except:
		pass
   
# Da ottimizzare il piu' possibile
def main():
	print 'starting'
	
    rgb = set([])
    for r1 in range (0, 256):
        for g1 in range (0, 256):
            for b1 in range (0, 256):
                rgb += '{:0{}X}'.format(r1, 2) + '{:0{}X}'.format(g1, 2) + '{:0{}X}'.format(b1, 2)
    
	print 'rgb values: ' + len(rgb)
	
	rgb12 = []
	for rgb1 in rgb:
		for rgb2 in rgb:
			rgb12 += '+' + b + '+' + c
			
	print 'rgb12 values: ' + len(rgb12)
	
    for x in range(0, 10000):
        for y in range(0, 10000):
            xy = str(x) + ':' + str(y)
            for a in rgb12:
                tryOpen(xy + a)



if __name__ == "__main__":
    main()
