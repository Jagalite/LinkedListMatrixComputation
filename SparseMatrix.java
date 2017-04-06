
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SparseMatrix {

    //implement all pre-defined methods below and add your own methods as needed.
    
    private Node[] rowHeads;
    private Node[] colHeads;
    private int size;

    public SparseMatrix(Node[] r, Node[] c) {
        rowHeads = r;
        colHeads = c;
        size = r.length;
    }

    public static SparseMatrix initializeByInput(File file) throws Exception {
        SparseMatrix result = null;
        
        
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
			String line = br.readLine();
			int matrixSize = (Integer.valueOf(line));
			
	    	Node[] retRow = new Node[matrixSize];
	    	Node[] retCol = new Node[matrixSize];
	    	
	    	for(int i = 0; i < matrixSize; i++) {
	    		Node rowHead = new Node(-1, -1, -1);
	        	rowHead.rowLink = rowHead;
	        	retRow[i] = rowHead;
	        	
	        	Node colHead = new Node(-1, -1, -1);
	        	colHead.colLink = colHead;
	        	retCol[i] = colHead;
	    	}
	    	
			while ((line = br.readLine()) != null) {
				String[] split = line.split("	");
				
				//System.out.println("row: " + Integer.valueOf(split[0]) + " | col:" + Integer.valueOf(split[1]) + " | value: " + Integer.valueOf(split[2]));
				insertNode(retRow, retCol, Integer.valueOf(split[2]), Integer.valueOf(split[0])-1, Integer.valueOf(split[1])-1);
			}
			
			setSize(matrixSize);
			return new SparseMatrix(retRow, retCol);

		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return result;
    }

    //parameter n --> given matrix size n
    public static SparseMatrix[] initializeByFormula(int n) throws Exception {
        SparseMatrix[] result = new SparseMatrix[3];
        
        //Init B
        Node[] bRows = new Node[n];
        Node[] bColumns = new Node[n];
        
        //Init C
        Node[] cRows = new Node[n];
        Node[] cColumns = new Node[n];
        
        //Init D
        Node[] dRows = new Node[n];
        Node[] dColumns = new Node[n];
        
        for(int i = 0; i < n; i++) {
        	Node bRowsNode = new Node(-1, -1, -1);
        	bRowsNode.rowLink = bRowsNode;
        	bRows[i] = bRowsNode;
        	
        	
        	Node bColumnsNode = new Node(-1, -1, -1);
        	bColumnsNode.colLink = bColumnsNode;
        	bColumns[i] = bColumnsNode;
        	
        	
        	
        	Node cRowsNode = new Node(-1, -1, -1);
        	cRowsNode.rowLink = cRowsNode;
        	cRows[i] = cRowsNode;
        	
        	Node cColumnsNode = new Node(-1, -1, -1);
        	cColumnsNode.colLink = cColumnsNode;
        	cColumns[i] = cColumnsNode;
        	
        	Node dRowsNode = new Node(-1, -1, -1);
        	dRowsNode.rowLink = dRowsNode;
        	dRows[i] = dRowsNode;
        	
        	Node dColumnsNode = new Node(-1, -1, -1);
        	dColumnsNode.colLink = dColumnsNode;
        	dColumns[i] = dColumnsNode;
        }
        
        //Generate B
        for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) if(i == j) insertNode(bRows, bColumns, i+1, i, j);
        
        //Generate C
        for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) if((i+1) == ((j+2) % n)) {
        	insertNode(cRows, cColumns, (-2*(j+1))-(i+1), i, j); 
        }
        
        //Generate D
        for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) {
        	if((j != 3) && (i % 2 != 0) && (j % 2 != 0)) insertNode(dRows, dColumns, i+j, i-1, j-1);
        	if(j == 3) insertNode(dRows, dColumns, -1*(i+1), i, j-1);
        }
        
        result[0] = new SparseMatrix(bRows, bColumns);
        result[1] = new SparseMatrix(cRows, cColumns);
        result[2] = new SparseMatrix(dRows, dColumns);
        
        return result;
    }

    public void print() {
    	for(int i = 0; i < size; i++) {
            System.out.println(rowToString(this.rowHeads[i].rowLink));
        }
    }

    //parameter m --> another sparse matrix m
    // public SparseMatrix add(SparseMatrix m) throws Exception {
    // 	Node[] retRow = new Node[size];
    // 	Node[] retCol = new Node[size];
    	
    // 	for(int i = 0; i < rowHeads.length; i++) {
    // 		Node rowHead = new Node(-1, -1, -1);
    //     	rowHead.rowLink = rowHead;
    //     	retRow[i] = rowHead;
        	
    //     	Node colHead = new Node(-1, -1, -1);
    //     	colHead.colLink = colHead;
    //     	retCol[i] = colHead;
    // 	}
        
    //     for(int i = 0 ; i < rowHeads.length; i++) {
    //     	processTwoRowsAddition(rowHeads[i].rowLink, m.rowHeads[i].rowLink, retRow, retCol);
    //     }
        
    //     return new SparseMatrix(retRow, retCol);
    // }

    //parameter m --> another sparse matrix m
    // public SparseMatrix subtract(SparseMatrix m) throws Exception {
    //     SparseMatrix result = m.scalarMultiply(-1);
    //     return this.add(result);
    // }

    //integer a
    // public SparseMatrix scalarMultiply(int a) throws Exception {
    //   // SparseMatrix result = null;
        
    // 	Node[] retRow = new Node[size];
    // 	Node[] retCol = new Node[size];
    	
    // 	for(int i = 0; i < rowHeads.length; i++) {
    // 		Node rowHead = new Node(-1, -1, -1);
    //     	rowHead.rowLink = rowHead;
    //     	retRow[i] = rowHead;
        	
    //     	Node colHead = new Node(-1, -1, -1);
    //     	colHead.colLink = colHead;
    //     	retCol[i] = colHead;
    // 	}
    	
    	
    //     for(int i = 0 ; i < rowHeads.length; i++) recursiveScalarMultiply(rowHeads[i].rowLink, retRow, retCol, a);
        
        
    //     return new SparseMatrix(retRow, retCol);
    // }

    //parameter m --> another sparse matrix m
    // public SparseMatrix matrixMultiply(SparseMatrix m) throws Exception {
    //     Node[] retRow = new Node[size];
    // 	Node[] retCol = new Node[size];
    	
    // 	for(int i = 0; i < rowHeads.length; i++) {
    // 		Node rowHead = new Node(-1, -1, -1);
    //     	rowHead.rowLink = rowHead;
    //     	retRow[i] = rowHead;
        	
    //     	Node colHead = new Node(-1, -1, -1);
    //     	colHead.colLink = colHead;
    //     	retCol[i] = colHead;
    // 	}
    	
    // 	for(int i = 0; i < rowHeads.length; i++) {
    // 		for(int j = 0; j < m.colHeads.length; j++) {
    // 			insertNode(retRow, retCol, dotProductRowAndColumn(this.getRow(i), m.getCol(j)), i, j);
    // 		}
    // 	}
    	
    // 	return new SparseMatrix(retRow, retCol);
    // }

    //integer c
    public SparseMatrix power(int c) {
        SparseMatrix result = null;
        return result;
    }
    
    //transpose matrix itself
    public SparseMatrix transpose() {
        SparseMatrix result = null;
        return result;
    }


    //Helper Functions    
    public int getSize(){
        return size;
    }
    
    public static void setSize(int size) {
    	size = size;
    }
    
    public static void insertNode(Node[] rows, Node[] columns, int value, int rowIndex, int columnIndex) throws Exception {
    	if(rowIndex > rows.length-1 || columnIndex > columns.length-1) throw new Exception("Index out bounds - insertNode()");
    	
    	//System.out.println(value + " | " + rowIndex + " | " + columnIndex);
    	
    	Node rowNode = rows[rowIndex];
    	Node columnNode = columns[columnIndex];
    	Node newNode = new Node(value, rowIndex, columnIndex);
    	
    	while(rowNode.rowLink.row != -1 && rowNode.rowLink.col < columnIndex) rowNode = rowNode.rowLink;
    	Node rowTemp = rowNode.rowLink;
    	rowNode.rowLink = newNode;
    	newNode.rowLink = rowTemp;
    	
    	while(columnNode.colLink.col != -1 && columnNode.colLink.row < rowIndex) columnNode = columnNode.colLink;
    	Node colTemp = columnNode.colLink;
    	columnNode.colLink = newNode;
    	newNode.colLink = colTemp;
    	
    }
    
//     public void processTwoRowsAddition(Node row1, Node row2, Node[] retRow, Node[] retCol) throws Exception {
//     	if(row1.col == -1 && row2.col == -1) {
//     	    return;
//     	}
    	
    	
//     	if(row1.col == -1) {
//     		insertNode(retRow, retCol, (int) row2.value, row2.row, row2.col);
//     		processTwoRowsAddition(row1, row2.rowLink, retRow, retCol);
// 		return;
//     	}
    	
//     	if(row2.col == -1) {
//     		insertNode(retRow, retCol, (int) row1.value, row1.row, row1.col);
//     		processTwoRowsAddition(row1.rowLink, row2, retRow, retCol);
// 		return;
//     	}
    	
//     	if(row1.col != -1 && row2.col != -1) {
//     		if(row1.col == row2.col) {
//     			insertNode(retRow, retCol, (int) row1.value + (int) row2.value, row1.row, row1.col);
//         		processTwoRowsAddition(row1.rowLink, row2.rowLink, retRow, retCol);
// 			return;
//     		}
    		
//         	if(row1.col < row2.col) {
//         		insertNode(retRow, retCol, (int) row1.value, row1.row, row1.col);
//         		processTwoRowsAddition(row1.rowLink, row2, retRow, retCol);
//         	}
//         	else {
//         		insertNode(retRow, retCol, (int) row2.value, row2.row, row2.col);
//         		processTwoRowsAddition(row1, row2.rowLink, retRow, retCol);
//         	}
//     	}
    	
//     }
    
//     public void recursiveScalarMultiply(Node current, Node[] rows, Node[] cols, int scalar) throws Exception {
//     	if(current.row != -1) {
//     		insertNode(rows, cols, (int)current.value*scalar, current.row, current.col);
//     		recursiveScalarMultiply(current.rowLink, rows, cols, scalar);
//     	}
//     }
    
//     public int dotProductRowAndColumn(Node row, Node column) {
//     	if(row.col == -1 || column.row == -1) return 0;
//     	if(row.col < column.row) return dotProductRowAndColumn(row.rowLink, column);
//     	if(row.col > column.row) return dotProductRowAndColumn(row, column.colLink);
    	
//     	if(row.col == column.row) {
//     		return dotProductRowAndColumn(row.rowLink, column.colLink) + (int)(row.value*column.value);
//     	}
//     	return 0;
    	
//     }
    
//     public Node[] getRows() {
//     	return rowHeads;
//     }
    
//     public Node[] getCols() {
//     	return colHeads; 
//     }
    
//     public Node getRow(int row) {
//     	return rowHeads[row].rowLink;
//     }
    
//     public Node getCol(int col) {
//     	return colHeads[col].colLink;
//     }
    
    public String rowToString(Node current) {
    	if(current.col == -1) return "";
    	return current.value + "|" + rowToString(current.rowLink);
    }
    
//     public String colToString(Node current) {
//     	if(current.row == -1) return "";
//     	return current.value + "|" + colToString(current.colLink);
//     }

//     public boolean isBitSet(int num, int index) {
// 	    int mask = 1 << index;
// 	    if((num & mask) == 0) return false;
// 	    return true;
//     }
}
