#implement binary search

def bin_search(arr, x):
    i = 0
    j = len(arr)
    while i < j:
        mid = int((i + j) / 2)
        if arr[mid] == x:
            return(True, mid)
        elif arr[mid] < x:
            i = mid+1
        else:
            j = mid
    return(False, i)


print(bin_search([1,2,3,7], 0))
print(bin_search([1,2,3,7], 3))
print(bin_search([1,2,3,7], 5))
print(bin_search([1,2,3,7], 10))
print(bin_search([1,2,3,7,9], 0))
print(bin_search([1,2,3,7,9], 2))
print(bin_search([1,2,3,7,9], 5))
print(bin_search([1,2,3,7,9], 10))
        
#implement generalized binary search
def gen_bin_search(begin, end, f, x):
    i = begin
    j = end
    while i < j:
        mid = int((i + j) / 2)
        if f(mid) < x:
            i = mid+1
        else:
            j = mid
    return(i)


print(gen_bin_search(2, 9, lambda x: 1 if (x > 5) else 0, 1))
print(gen_bin_search(3, 9, lambda x: 0 if (x < 2) else 1, 1))
print(gen_bin_search(3, 9, lambda x: 1 if (x > 10) else 0, 1))
print(gen_bin_search(2, 9, lambda x: 1 if (x > 10) else 0, 1))


