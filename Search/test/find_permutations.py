import os
import sys
import itertools

symbols = ['-']

for N in range(1, 7):
    name = "perms_" + str(N) + ".txt"
    file = open(name, 'w')
    symbols = symbols + ['M', 'A']
    perm = set(itertools.permutations(symbols))
    perm = [''.join(a) for a in perm]
    print("Total permutations of N = %d:" % N, len(perm))
    for permutation in perm:
        file.write(permutation + "\n")
    file.close()
        
