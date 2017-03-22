// ============================================================================
// MATRIX DISTANCE
// ============================================================================
package main

import (
  "os"
  "encoding/csv"
  "strconv"
  "math"
  "fmt"
  "time"
)

func main(){
  // read input file
  fmt.Println("Reading")
  inputMatrix := readMatrix()

  // calculate distance matrix
  fmt.Println("Calculating")
  start := time.Now()
  outputMatrix := calculateMatrix(inputMatrix)
  fmt.Println(time.Since(start))

  // save distance matrix
  
  for i := 0; i < 10; i++{    
    var row []float32 = outputMatrix[i]
    var sum float32 = 0.0
    for j := 0; j < len(row); j++{    
        sum += row[j]
    }
    fmt.Println(i, " = ", sum)
    fmt.Println(outputMatrix[i])
  }
}

// =============================================================================
// STEP 1
// =============================================================================
func readMatrix() [][]float32 {
  // open file
  f, _ := os.Open("data/matrix_distance_input.csv")
  r := csv.NewReader(f)

  // read all lines to memory to iterate
  lines, _ := r.ReadAll()

  // generate empty matrix
  inputMatrix := make([][]float32, len(lines))
  for i := 0; i < len(lines); i++ {
    inputMatrix[i] = make([]float32, len(lines[0]))
  }

  // iterate rows and columns the matrix values
  for rowIndex, row := range lines{
    for columnIndex, column := range row{
      columnValue, _ := strconv.ParseFloat(column, 64)
      inputMatrix[rowIndex][columnIndex] = float32(columnValue)
    }
  }

  return inputMatrix
}

// =============================================================================
// STEP 2
// =============================================================================
func calculateMatrix(inputMatrix[][]float32) [][]float32 {
  // generate matrix
  outputMatrix := make([][]float32, len(inputMatrix))
  for i := 0; i < len(outputMatrix); i++ {
    outputMatrix[i] = make([]float32, len(inputMatrix))
  }

  // iterate base
  for i := 0; i < len(outputMatrix); i++ {
    go calculateDistaceBetweenRows(i, inputMatrix, outputMatrix)
  }
  return outputMatrix
}

func calculateDistaceBetweenRows(i int, inputMatrix[][]float32, outputMatrix[][]float32){
  for j := i; j < len(outputMatrix); j++ {
    dist := cosineDistance(inputMatrix[i], inputMatrix[j])
    outputMatrix[i][j] = dist
    outputMatrix[j][i] = dist
  }
}

func cosineDistance(p1[]float32, p2[]float32) float32 {
    var dotProduct float32 = 0.0
    var normA float32 = 0.0
    var normB float32 = 0.0
    for i := 0; i < len(p1); i++ {
        dotProduct += p1[i] * p2[i]
        normA += p1[i] * p1[i]
        normB += p2[i] * p2[i]
    }
    return float32(float64(dotProduct) / (math.Sqrt(float64(normA)) * math.Sqrt(float64(normB))))
 }
