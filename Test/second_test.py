import os
import sys
import itertools

os.system("javac *.java")
open('start.txt', 'w').close()
open('value.txt', 'w').close()
open('heur.txt', 'w').close()

perm = set(itertools.permutations(['M','M','M','A','A','A','-']))
perm = [''.join(a) for a in perm]


print("Starting to use search")
for i in range(len(perm)):
    os.system("java Search2 "+perm[i])

print("Now find heuristic values")
with open("start.txt",'r') as starts:
    for line in starts.readlines():
        os.system("java Heuristic "+ line)

print("Check the values")
counter = 0
fail_counter = 0
fail_indexes = []
with open("value.txt",'r') as result, open("heur.txt",'r') as heur:
    for real in result.readlines():
        if real == '': break
        real_value = int(real)
        heur_value = int(heur.readline())
        if real_value != -1 and (real_value<heur_value):
            fail_indexes.append(counter)
        counter +=1;
print(counter," tests ran")
print(len(fail_indexes), " tests failed")
counter = 0;
print("Failed Data")
print("Starting Seq - Actual Cost - Heuristic value")
with open("value.txt",'r') as result, open("heur.txt",'r') as heur,\
open("start.txt",'r') as start:
    start_line = start.readline();
    real_value = result.readline();
    heur_value = heur.readline();
    while start_line:
        if counter in fail_indexes:
            print(start_line.strip(), real_value.strip(), heur_value.strip())
        counter+=1
        start_line = start.readline();
        real_value = result.readline();
        heur_value = heur.readline();
