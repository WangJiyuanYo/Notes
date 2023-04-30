package main

import (
	"fmt"
	"sort"
)

func numMovesStones(a, b, c int) []int {
	p := []int{a, b, c}
	sort.Ints(p)
	a, b, c = p[0], p[1], p[2]
	minMove := 0
	if c-a > 2 {
		if b-a <= 2 || c-b <= 2 {
			minMove = 1
		} else {
			minMove = 2
		}
	}
	return []int{minMove, c - a - 2}
}

func main() {
	arrays := numMovesStones(1, 2, 5)
	for _, value := range arrays {
		fmt.Printf("%d\n", value)
	}
}
