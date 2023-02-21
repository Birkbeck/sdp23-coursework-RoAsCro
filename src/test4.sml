mov EAX 3
mov EBX 1
mov ECX 1
a: sub EAX EBX
out EAX
jnz EAX a
out EBX
jnz EBX b
add EBX ECX
b: sub EBX ECX
out EBX
