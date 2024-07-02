package test

import (
	"fmt"
	"testing"
)

func TestChecksum(t *testing.T) {
	head := []byte("\x47\x00\x00\x5c\xa8\x1a\x40\x00\x40\x01\x22\xc7\xac\x16\xd6\x3c\x9d\x94\x45\x50\x01\x83\x07\x04\x00\x00\x00\x01")
	for _, b := range head {
		fmt.Printf("%x ", b)
	}
	fmt.Println()

	fmt.Printf("source checksum: 0x%x%x\n", head[10], head[11])
	// 将checksum位置设置为0
	head[10] = 0
	head[11] = 0

	res := checksum(head[:])
	fmt.Printf("res: 0x%x\n", res)
}

// rfc 1071
func checksum(b []byte) uint16 {
	var sum uint32

	// 每16位加起来
	l := len(b)
	i := l
	for i > 1 {
		sum += uint32(b[l-i])<<8 | uint32(b[l-i+1])
		i -= 2
	}

	// 如果还有继续加
	if i > 0 {
		sum += uint32(b[l-i])
	}

	// 回卷。例如：0x3ffff 用 0x3 + 0xffff 得到 0x10003。继续 0x1 + 0x3 得到 0x4
	for sum>>16 != 0 {
		sum = (sum & 0xffff) + (sum >> 16)
	}

	// 反码
	return ^uint16(sum)
}
