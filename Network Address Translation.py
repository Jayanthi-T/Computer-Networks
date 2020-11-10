import random
def read():
	fl= []
	with open("natt.txt") as f:
		fl = f.readlines()
		f.close()  
	for line in fl: 
		nat_table.append(line.split()) 
	
def write(val):  
	if val == 2:
		with open("natt.txt", 'w') as f:
			for ip in nat_table:
				s = ip[0]+"\t"+ip[1]+"\n"
				f.write(str(s))
			f.close()
	else:
		with open("natt.txt", 'w') as f:
			f.close()

	
def nat():
	dl = []
	wan_ip = "192.68.10.x"
	lan_ip = str(input("Enter LAN IP:"))
	flag = 0
	for ip in nat_table:
		if ip[0] == lan_ip:
			print("IP exist\tWAN IP: ", ip[1])
			flag =1
			break
	
	if flag == 0:
		dl.append(lan_ip)
		r = random.randint(0,255)
		wan_ip = wan_ip.replace('x', str(r))
		dl.append(wan_ip)
		print("WAN IP: ", wan_ip)
		nat_table.append(dl)

def table():
	print("NAT table:")
	for i in nat_table:
		print(i)
	
	
if __name__== "__main__":
	nat_table = []
	read()
	while True:
		val = int(input("1. Display NAT Table\n2. Assign address\n3. Exit\n"))
		if val == 1:
			table()
		elif val == 2:
			nat()
		elif val==3:
			c = int(input("Do you want to delete IP?\n1. yes\n2. No"))
			write(c)
			break
	print("exit") 
