package test

import (
	"fmt"
	"testing"
)

func add_around_water(grid [][]int) [][]int {
	newLen := len(grid[0]) + 2

	var res = make([][]int, 0, newLen)

	water := make([]int, newLen, newLen)

	res = append(res, water)

	for i := 0; i < len(grid); i++ {
		var toAdd []int
		toAdd = append(toAdd, 0)
		toAdd = append(toAdd, grid[i]...)
		toAdd = append(toAdd, 0)
		res = append(res, toAdd)
	}

	res = append(res, water)
	return res
}

func count_new_land(row []int, pre_row []int) int {
	var res = 0
	lastLand := false
	isNewLand := true
	for i := 0; i < len(row); i++ {
		if row[i] == 1 {
			// 开始陆地
			if pre_row[i] != 0 {
				isNewLand = false
			}
			lastLand = true
		} else if lastLand && row[i] == 0 {
			if isNewLand {
				res += 1
			}
			isNewLand = true
			lastLand = false
		}
	}

	return res
}

func TestMain(t *testing.T) {
	grid := [][]int{
		{0, 0, 0, 0, 0},
		{0, 1, 0, 1, 0},
		{0, 1, 0, 1, 0},
		{0, 1, 1, 1, 1},
		{0, 0, 0, 0, 0},
	}

	new_grid := add_around_water(grid)

	fmt.Printf("%v", new_grid)

	count := 0
	for i := 1; i < len(new_grid); i++ {
		count += count_new_land(new_grid[i], new_grid[i-1])
	}

	fmt.Println(count)
}
