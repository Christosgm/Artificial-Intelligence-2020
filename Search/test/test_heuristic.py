import os
import sys

file_number = input("N = ")

name = "perms_" + file_number + ".txt"
file = open(name, "r").read().splitlines()
for perm in range(1, len(file)+1):
    permutation = file[perm - 1]
    print("Calculating heuristic value with permutation %d:" % perm, permutation)
    os.system("java NewHeuristic "+ permutation)
    
