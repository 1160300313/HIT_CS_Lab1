package P1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MagicSquare {

	private static int square[][] = new int[999][999];
	{
		for (int i = 0; i < 999; i++)
			for (int j = 0; j < 999; j++)
				square[i][j] = 0;
	}

	private static boolean isLegalMagicSquare(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		int line = 0;// 行数
		int row = 0;// 列数
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			int limit = 0;
			while ((temp = reader.readLine()) != null) {
				line++;
				String lineSplited[] = new String[999];
				lineSplited = temp.split("\t");
				row = lineSplited.length;
				if (line == 1)
					limit = row; // 设置行数、列数的“标杆”
				else {
					if (limit != row) { // 只要有一行的列数与第一行不同就必定不是矩阵
						System.out.println("ERROR 非矩阵 or 非法分隔符");
						return false;
					}
				}
				// System.out.println("line=" + line + ";row=" + row);
				// System.out.println(lineSplited.length);
				int i;
				for (i = 1; i <= row; i++) {
					try {
						int element = Integer.parseInt(lineSplited[i - 1]);
						if (element > 0)
							square[line][i] = element;
						else {
							System.out.println("ERROR 非正整数");
							return false;
						}
					} catch (NumberFormatException e) {
						System.out.println("ERROR 非正整数");
						return false;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (row != line) {
			System.out.println("ERROR 行列不等");
			return false;
		}
		// System.out.println("row=" + row);
		int i, j;// 循环变量
		int sumAll, sumRow, sumLine, sumCross1, sumCross2;

		// for (i = 1; i <= line; i++) {
		// for (j = 1; j <= row; j++)
		// System.out.print(square[i][j] + "\t");
		// System.out.println();
		// }

		sumAll = sumRow = sumLine = sumCross1 = sumCross2 = 0;
		// 第一次循环:判断各行及两个对角线的和
		for (i = 1; i <= line; i++) {
			sumLine = 0;
			sumCross1 += square[i][i];
			sumCross2 += square[i][row - i + 1];
			for (j = 1; j <= row; j++) {
				sumLine += square[i][j];
			}
			if (i == 1)
				sumAll = sumLine;
			if (sumLine != sumAll) {
				System.out.println("ERROR 和不同");
				return false;
			}
		}
		if (sumCross1 != sumAll || sumCross2 != sumAll) {
			System.out.println("ERROR 和不同");
			return false;
		}
		// 第二次循环:判断各列的和
		for (j = 1; j <= row; j++) {
			sumRow = 0;
			for (i = 1; i <= line; i++) {
				sumRow += square[i][j];
			}
			if (sumRow != sumAll) {
				System.out.println("ERROR 和不同");
				return false;
			}
		}
		return true;
	}

	public static boolean generateMagicSquare(int n) {
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		try {
			for (i = 1; i <= square; i++) {
				magic[row][col] = i;
				if (i % n == 0)
					row++;
				else {
					if (row == 0)
						row = n - 1;
					else
						row--;
					if (col == (n - 1))
						col = 0;
					else
						col++;
				}
			}
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					System.out.print(magic[i][j] + "\t");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (i = 0; i < 10000; i++)
				;
			return false;
		}
		// 写文件
		File outFile = new File("src/P1/txt/6.txt");
		try {
			if (!outFile.exists())
				outFile.createNewFile();
			FileWriter out = new FileWriter(outFile);
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					out.write(magic[i][j] + "\t");
				out.write("\r\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(isLegalMagicSquare("src/P1/txt/1.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/2.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/3.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/4.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/5.txt"));
		System.out.println(generateMagicSquare(111));
		generateMagicSquare(111);
		System.out.println(isLegalMagicSquare("src/P1/txt/6.txt"));
	}

}
