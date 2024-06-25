package string

import (
	"fmt"
	"testing"
)

func TestDetectCaptitalUse(testing *testing.T) {
	word := "FlaG"
	fmt.Printf("FlaG %v\n", detectCapitalUse(word))

	word = "ggg"
	fmt.Printf("ggg %v\n", detectCapitalUse(word))

	word = "FFFFFFFFFFFFFFFFFFFFf"
	fmt.Printf("FFFf %v\n", detectCapitalUse(word))

	word = "USA"
	fmt.Printf("USA %v\n", detectCapitalUse(word))
}
