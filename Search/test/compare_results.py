file_number = input("N = ")

name_actual = "actual_" + file_number + ".txt"
name_heur = "heur_" + file_number + ".txt"

perms = "perms_"+str(file_number)+".txt"
file_perms = open(perms, 'r').read().splitlines()
file_actual = open(name_actual, "r").read().splitlines()
file_heur = open(name_heur, "r").read().splitlines()

name_defectives = "defectives_" + str(file_number) + ".txt"

file_defective = open(name_defectives, 'w')
defectives = 0

file_defective.write('Permutation Actual Heuristic\n')
for i in range(len(file_actual)):
    print("Testing case %d" %(i+1))
    if int(file_actual[i]) < int(file_heur[i]) and int(file_actual[i]) != -1:
        defectives += 1
        msg = file_perms[i] + ' ' + file_actual[i] + ' ' + file_heur[i]
        file_defective.write(msg + "\n")
if(defectives == 0):
    print("No tests failed!")
    file_defective.write("Passed!\n")
else:
    print(defectives, "tests failed!")
file_defective.close()
