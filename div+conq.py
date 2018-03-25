# Takes an array of integers and returns the subarry of largest sum


def max_cross(arr, ind):
    current_max = arr[ind]
    lstep = 1
    while ind-lstep >= 0:
        if arr[ind - lstep] >= 0:
            current_max += arr[ind - lstep]
            lstep += 1
        else:
            break
    rstep = 1
    while ind+rstep < len(arr):
        if arr[ind + rstep] >= 0:
            current_max += arr[ind + rstep]
            rstep += 1
        else:
            break
    return current_max


def max_subarr(arr):
    if len(arr) == 1:
        return(arr[0])
    mid = int(len(arr)/2)
    return(max(max_subarr(arr[:mid]), max_subarr(arr[mid:]), 
           max_cross(arr, mid)))


print(max_subarr([1, 2, -3, 3, -5])) 
print(max_subarr([1, 2, 3, -5])) 
print(max_subarr([1, 2, 3])) 

# Sort an array with quicksort


def quicksort(arr):
    if (len(arr) == 1 or len(arr) == 0):
        return arr
    pivot = arr[0]
    less_than = []
    equal_to = []
    greater_than = []
    for elem in arr:
        if elem > pivot:
            greater_than.append(elem)
        elif elem < pivot:
            less_than.append(elem)
        else:
            equal_to.append(elem)
    return (quicksort(less_than) + equal_to + quicksort(greater_than))
    

print(quicksort([3, 2, 1, 5]))


        