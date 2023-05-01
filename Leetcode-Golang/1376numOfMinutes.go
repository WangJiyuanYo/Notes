package main

func numOfMinutes(n, _ int, manager, informTime []int) (ans int) {
	memo := make([]int, n)
	for i := range memo {
		memo[i] = -1 // -1 表示还没有计算过
	}
	var dfs func(int) int
	dfs = func(x int) int {
		if manager[x] < 0 {
			return informTime[x]
		}
		if memo[x] >= 0 { // 之前计算过了
			return memo[x]
		}
		res := dfs(manager[x]) + informTime[x]
		memo[x] = res // 记忆化
		return res
	}
	for i := range manager {
		ans = max(ans, dfs(i))
	}
	return
}

func max(a, b int) int {
	if a < b {
		return b
	}
	return a
}

func main() {

}
