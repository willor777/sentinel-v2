package com.willor.sentinel_v2


fun sortArr(arr: Array<Int>): Array<Int>{
    while( true){
        println("Looped")
        var moves = 0
        for (i in 0..arr.lastIndex){
            if (i == arr.lastIndex){
                break
            }

            val cur = arr[i]
            val next = arr[i + 1]

            if (cur > next) {
                arr[i + 1] = cur
                arr[i] = next
                moves += 1
            }
        }
        if (moves == 0){
            break
        }
    }

    return arr
}


fun main() {
    val myIntAr = arrayOf(1, 22, 3, 44, 5, 66)

    val t = sortArr(myIntAr)

    for (v in t){
        println(v)
    }
}