# max subarry sum using dynamic programming, previously done with DC


def max_subarr(arr):
    if len(arr) == 1:
        return(arr[0])
    return(max(max_subarr(arr[1:]), arr[0] + max_subarr_from(arr[1:])))


def max_subarr_from(arr):
    return(max([sum(arr[:j+1]) for j in range(len(arr))]))


print(max_subarr([-1, 11, -10, 4, 20]))

# easy practice with memoization

fib = {1: 1, 2: 1}


def memofib(n):
    if n in fib:
        return fib[n]
    else:
        f = memofib(n-1) + memofib(n-2)
        fib[n] = f
        return f


# knapsack problem using memoization. items is value, weight list, no decorator

memosack = {}


def knapsack(items, maxweight):
    if (items, maxweight) in memosack:
        return(memosack[(items, maxweight)])
    else:
        if (len(items) == 0 or maxweight <= 0):
            memosack[(items, maxweight)] = 0
            return 0
        else:
            value, weight = items[0]
            if weight > maxweight:
                return knapsack(items[1:], maxweight)
            else:
                k = max(knapsack(items[1:], maxweight), 
                        value + knapsack(items[1:], maxweight - weight))
                memosack[(items, maxweight)] = k
                return k


stuff = ((4, 12), (2, 1), (6, 4), (1, 1), (2, 2))
print(knapsack(stuff, 15))





    
