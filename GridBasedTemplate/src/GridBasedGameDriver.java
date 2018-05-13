
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

public class GridBasedGameDriver {

	private static Location clickedLoc;
	private static int[][] grid = new int[8][8];
	private static int current = -1;
	private static int whiteScore = 2;
	private static int blackScore = 2;
	private static boolean valid = false;
	private static boolean mark;
	private static boolean computer;
	private static Location compLoc;

	static World world2 = new World();

	static World world = new World() {

		@Override
		public boolean locationClicked(Location loc) {

			clickedLoc = loc;

			play();

			return true;

		}

		void play() {

			
			
			
			turnOver(clickedLoc, false, true);
			for (int r = 0; r < grid.length; r++) {
				for (int c = 0; c < grid.length; c++) {
					if (grid[r][c] == 2) {
						grid[r][c] = 0;
					}

				}
			}
			if (computer) {
				compPlay();
			}
			else {
				mark();
			}

		
			

		}

		void mark() {
			for (int r = 0; r < grid.length; r++) {
				for (int c = 0; c < grid.length; c++) {
					if (grid[r][c] == 0) {
						Location loc2 = new Location(r, c);
						turnOver(loc2, mark, false);
					}

				}
			}
			showGrid();

			

		}

		void compPlay() {
			Timer timer = new Timer();

			timer.schedule(new TimerTask() {
				@Override
				public void run() {

					if (current == 1) {
//						System.out.println("***Current is one***");
						ArrayList<Location> p = new ArrayList();
						for (int r = 0; r < grid.length; r++) {
							for (int c = 0; c < grid.length; c++) {
								if (grid[r][c] == 0 || grid[r][c] == 2) {
									Location loc2 = new Location(r, c);
									turnOver(loc2, false, false);
									if (compLoc != null) {
										p.add(compLoc);
//										System.out.println("Adding");
									}

								}

							}
						}

//						System.out.println(p);
						int random = (int) (Math.random() * p.size());
						System.out.println(random);
						if(p.size() >0) {
							Location loc3 = p.get(random);
							turnOver(loc3, false, true);
						}
						else {
							current = current*-1;
						}
						mark();
						
						// grid[loc2.getRow()][loc2.getCol()] = grid[loc2.getRow()][loc2.getCol()] * -1;
					}
				}

			}, 15 * 60);

		}

		void turnOver(Location loc, boolean mark, boolean change) {
			// ****************** UP ***********************

			if (loc.getRow() > 1) {
				if (grid[loc.getRow() - 1][loc.getCol()] == current * -1) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					int c = loc.getCol();
					for (int r = loc.getRow() - 2; r > -1; r--) {

						if (grid[r][c] == current) {

							tillR = r;
							tillC = c;
							turn = true;
							r = -1;
						} else if (grid[r][c] != -1 * current) {
							r = -1; // --> to break out of for loop
						}

					}

					if (turn) {

						valid = true;
						for (int r = loc.getRow(); r > tillR; r--) {
							if (change) {
								grid[r][c] = current;
							}

						}
					}

				}

			}

			// ****************** DOWN ***********************

			if (loc.getRow() < grid.length - 2) {
				if (grid[loc.getRow() + 1][loc.getCol()] == -1 * current) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					int c = loc.getCol();
					for (int r = loc.getRow() + 2; r < grid.length; r++) {
						if (grid[r][c] == current) {
							tillR = r;
							tillC = c;
							turn = true;
							r = 100; // --> to break out of for loop
						} else if (grid[r][c] != -1 * current) {
							r = 100; // --> to break out of for loop
						}

					}

					if (turn) {

						valid = true;
						for (int r = loc.getRow(); r < tillR; r++) {
							if (change) {
								grid[r][c] = current;
							}

						}
					}

				}

			}

			// ****************** Left ***********************

			if (loc.getCol() > 1) {
				if (grid[loc.getRow()][loc.getCol() - 1] == current * -1) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					int r = loc.getRow();
					for (int c = loc.getCol() - 2; c > -1; c--) {

						if (grid[r][c] == current) {

							tillR = r;
							tillC = c;
							turn = true;
							c = -1;
						} else if (grid[r][c] != -1 * current) {
							c = -1; // --> to break out of for loop
						}

					}

					if (turn) {

						valid = true;
						for (int c = loc.getCol(); c > tillC; c--) {
							if (change) {
								grid[r][c] = current;
							}

						}
					}

				}

			}

			// ****************** Right ***********************

			if (loc.getCol() < grid.length - 2) {
				if (grid[loc.getRow()][loc.getCol() + 1] == -1 * current) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					int r = loc.getRow();
					for (int c = loc.getCol() + 2; c < grid.length; c++) {
						if (grid[r][c] == current) {
							tillR = r;
							tillC = c;
							turn = true;
							c = 100; // --> to break out of for loop
						} else if (grid[r][c] != -1 * current) {
							c = 100; // --> to break out of for loop
						}

					}

					if (turn) {

						valid = true;
						for (int c = loc.getCol(); c < tillC; c++) {
							if (change) {
								grid[r][c] = current;
							}

						}
					}

				}

			}

			// ****************** UP - LEFT ***********************

			if (loc.getRow() > 1 & loc.getCol() > 1) {
				if (grid[loc.getRow() - 1][loc.getCol() - 1] == current * -1) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					// int c = loc.getCol();
					for (int a = 1; a <= grid.length; a++) {
						// for (int r = loc.getRow() - 2; r > -1; r--) {
						//
						// for (int c = loc.getCol() - 2; c > -1; c--) {

						int r = loc.getRow();
						int c = loc.getCol();

						if (r - a > -1 && c - a > -1) {
							if (grid[r - a][c - a] == current) {

								tillR = r - a;
								tillC = c - a;
								turn = true;
								a = 100;
							} else if (grid[r - a][c - a] != -1 * current) {
								a = 100; // --> to break out of for loop
							}

						}

					}

					if (turn) {

						valid = true;
						int difference = Math.abs(tillR - loc.getRow());
						for (int a = 0; a < difference; a++) {
							// for (int c = loc.getCol(); c > tillC; c--) {
							if (change) {
								grid[loc.getRow() - a][loc.getCol() - a] = current;
							}

							// }

						}
					}

				}
			}

			// ****************** DOWN-RIGHT ***********************

			if (loc.getRow() < grid.length - 2 & loc.getCol() < grid.length - 2) {
				if (grid[loc.getRow() + 1][loc.getCol() + 1] == -1 * current) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					// int c = loc.getCol();
					for (int a = 1; a <= grid.length; a++) {

						int r = loc.getRow();
						int c = loc.getCol();

						if (r + a < grid.length && c + a < grid.length) {
							if (grid[r + a][c + a] == current) {

								tillR = r + a;
								tillC = c + a;
								turn = true;
								a = 100;
							} else if (grid[r + a][c + a] != -1 * current) {
								a = 100; // --> to break out of for loop
							}
						}

					}

					if (turn) {

						valid = true;
						int difference = Math.abs(tillR - loc.getRow());
						for (int a = 0; a < difference; a++) {
							// for (int c = loc.getCol(); c > tillC; c--) {
							if (change) {
								grid[loc.getRow() + a][loc.getCol() + a] = current;
							}

							// }

						}
					}

				}

			}

			// ****************** UP - RIGHT ***********************

			if (loc.getRow() > 1 & loc.getCol() < grid.length - 2) {
				if (grid[loc.getRow() - 1][loc.getCol() + 1] == current * -1) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					// int c = loc.getCol();
					for (int a = 1; a <= grid.length; a++) {
						// for (int r = loc.getRow() - 2; r > -1; r--) {
						//
						// for (int c = loc.getCol() - 2; c > -1; c--) {

						int r = loc.getRow();
						int c = loc.getCol();

						if (r - a > -1 && c + a < grid.length) {
							if (grid[r - a][c + a] == current) {

								tillR = r - a;
								tillC = c + a;
								turn = true;
								a = 100;
							} else if (grid[r - a][c + a] != -1 * current) {
								a = 100; // --> to break out of for loop
							}

						}

					}

					if (turn) {

						valid = true;
						int difference = Math.abs(tillR - loc.getRow());
						for (int a = 0; a < difference; a++) {
							// for (int c = loc.getCol(); c > tillC; c--) {
							if (change) {
								grid[loc.getRow() - a][loc.getCol() + a] = current;
							}

							// }

						}
					}

				}
			}

			// ****************** DOWN-LEFT ***********************

			if (loc.getRow() < grid.length - 2 & loc.getCol() > 1) {
				if (grid[loc.getRow() + 1][loc.getCol() - 1] == -1 * current) {

					boolean turn = false;
					int tillR = 0;
					int tillC = 0;
					// int c = loc.getCol();
					for (int a = 1; a <= grid.length; a++) {

						int r = loc.getRow();
						int c = loc.getCol();

						if (r + a < grid.length && loc.getCol() - a > 1) {
							if (grid[r + a][c - a] == current) {

								tillR = r + a;
								tillC = c - a;
								turn = true;
								a = 100;
							} else if (grid[r + a][c - a] != -1 * current) {
								a = 100; // --> to break out of for loop
							}
						}

					}

					if (turn) {

						valid = true;
						int difference = Math.abs(tillR - loc.getRow());
						for (int a = 0; a < difference; a++) {
							// for (int c = loc.getCol(); c > tillC; c--) {
							if (change) {
								grid[loc.getRow() + a][loc.getCol() - a] = current;
							}

							// }

						}
					}

				}

			}

			if (valid && change) {
				current = current * -1;
			} else if (!mark) {
				System.out.println("Invalid Move");
			}

			if (mark && valid) {
				grid[loc.getRow()][loc.getCol()] = 2;
			}
			if (valid) {
				compLoc = loc;
			} else {
				compLoc = null;
			}

			showGrid();
			valid = false;
			// return null;

		}

		//

		@Override
		public boolean keyPressed(String key, Location loc) {
			// System.out.println("You just pressed the " + key + " key.");

			current = current * -1;
			showGrid();

			return false;
		}

	};

	public static void main(String[] args) {
		new GridBasedGameDriver().start();
		GridBasedGameDriver.showGrid();
	}

	private void start() {
		setUpGameBoard();
		world.show();// now the world is visible
		world2.show();

	}

	private void setUpGameBoard() {
		Grid gr = new BoundedGrid(8, 8);
		Grid gr2 = new BoundedGrid(2, 2);
		world.setGrid(gr);
		world2.setGrid(gr2);
		fillWithGreen();
		setFourPieces();
		showGrid();
		ask();

	}

	private void ask() {
		JFrame f = new JFrame();
		int a = JOptionPane.showConfirmDialog(f, "Show moves?");
		int b = JOptionPane.showConfirmDialog(f, "Computer Mode?");
		if (a == JOptionPane.YES_OPTION) {
			mark = true;
		} else {
			mark = false;
		}
		
		if (b == JOptionPane.YES_OPTION) {
			computer = true;
		} else {
			computer = false;
		}

	}

	private void fillWithGreen() {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				grid[r][c] = 0;
				world.add(new Location(r, c), new Green());
			}
		}

	}

	private void setFourPieces() {


		grid[3][3] = 1;

		grid[4][4] = 1;

		grid[3][4] = -1;

		grid[4][3] = -1;

	}

	private static void setScore() {
		whiteScore = 0;
		blackScore = 0;
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid.length; c++) {
				if (grid[r][c] == 1) {
					whiteScore++;
				} else if (grid[r][c] == -1) {
					blackScore++;
				}
			}
		}
	}

	private static void showGrid() {

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {

				if (grid[r][c] == 1) {

					world.add(new Location(r, c), new White());
				}

				else if (grid[r][c] == -1) {
					world.add(new Location(r, c), new Black());
				}

				else if (grid[r][c] == 2) {
					world.add(new Location(r, c), new Marked());

				}

				else {
					world.add(new Location(r, c), new Green());
				}
			}
		}

		// world2

		if (current == 1) {
			world2.add(new Location(0, 0), "WHITE!");
		}

		else {
			world2.add(new Location(0, 0), "BLACK!");
		}

		setScore();
		world2.add(new Location(1, 0), "w: " + whiteScore);
		world2.add(new Location(1, 1), "b: " + blackScore);

		if (whiteScore + blackScore == 64) {
			if (whiteScore > blackScore) {
				world2.add(new Location(0, 1), "White Wins");

			} else if (whiteScore < blackScore) {
				world2.add(new Location(0, 1), "Black Wins");
			} else {
				world2.add(new Location(0, 1), "TIE");
			}

		}

	}

}

// void turnOver() {
//
//
//
// // ****************** UP ***********************
//
// if (clickedLoc.getRow() > 1) {
// if (grid[clickedLoc.getRow() - 1][clickedLoc.getCol()] == current * -1) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// int c = clickedLoc.getCol();
// for (int r = clickedLoc.getRow() - 2; r > -1; r--) {
//
// if (grid[r][c] == current) {
//
// tillR = r;
// tillC = c;
// turn = true;
// r = -1;
// }
//
// }
//
// if (turn) {
//
// valid = true;
// for (int r = clickedLoc.getRow(); r > tillR; r--) {
// grid[r][c] = current;
//
// }
// }
//
// }
//
// }
//
// // ****************** DOWN ***********************
//
// if (clickedLoc.getRow() < grid.length - 2) {
// if (grid[clickedLoc.getRow() + 1][clickedLoc.getCol()] == -1 * current) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// int c = clickedLoc.getCol();
// for (int r = clickedLoc.getRow() + 2; r < grid.length; r++) {
// if (grid[r][c] == current) {
// tillR = r;
// tillC = c;
// turn = true;
// r = 100; // --> to break out of for loop
// }
//
// }
//
// if (turn) {
//
// valid = true;
// for (int r = clickedLoc.getRow(); r < tillR; r++) {
// grid[r][c] = current;
//
// }
// }
//
// }
//
// }
//
// // ****************** Left ***********************
//
// if (clickedLoc.getCol() > 1) {
// if (grid[clickedLoc.getRow()][clickedLoc.getCol() - 1] == current * -1) {
//
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// int r = clickedLoc.getRow();
// for (int c = clickedLoc.getCol() - 2; c > -1; c--) {
//
// if (grid[r][c] == current) {
//
//
// tillR = r;
// tillC = c;
// turn = true;
// c = -1;
// }
//
// }
//
// if (turn) {
//
//
// valid = true;
// for (int c = clickedLoc.getCol(); c > tillC; c--) {
// grid[r][c] = current;
//
// }
// }
//
// }
//
// }
//
// // ****************** Right ***********************
//
// if (clickedLoc.getCol() < grid.length - 2) {
// if (grid[clickedLoc.getRow()][clickedLoc.getCol() + 1] == -1 * current) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// int r = clickedLoc.getRow();
// for (int c = clickedLoc.getCol() + 2; c < grid.length; c++) {
// if (grid[r][c] == current) {
// tillR = r;
// tillC = c;
// turn = true;
// c = 100; // --> to break out of for loop
// }
//
// }
//
// if (turn) {
//
// valid = true;
// for (int c = clickedLoc.getCol(); c < tillC; c++) {
// grid[r][c] = current;
//
// }
// }
//
// }
//
// }
//
// // ****************** UP - LEFT ***********************
//
// if (clickedLoc.getRow() > 1 & clickedLoc.getCol() > 1) {
// if (grid[clickedLoc.getRow() - 1][clickedLoc.getCol() - 1] == current * -1) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// // int c = clickedLoc.getCol();
// for (int a = 1; a <= grid.length; a++) {
// // for (int r = clickedLoc.getRow() - 2; r > -1; r--) {
// //
// // for (int c = clickedLoc.getCol() - 2; c > -1; c--) {
//
// int r = clickedLoc.getRow();
// int c = clickedLoc.getCol();
//
// if (r - a > -1 && c - a > -1) {
// if (grid[r - a][c - a] == current) {
//
// tillR = r - a;
// tillC = c - a;
// turn = true;
// a = 100;
// }
//
// }
//
// }
//
// if (turn) {
//
// valid = true;
// int difference = Math.abs(tillR - clickedLoc.getRow());
// for (int a = 0; a < difference; a++) {
// // for (int c = clickedLoc.getCol(); c > tillC; c--) {
// grid[clickedLoc.getRow() - a][clickedLoc.getCol() - a] = current;
//
// // }
//
// }
// }
//
// }
// }
//
// // ****************** DOWN-RIGHT ***********************
//
// if (clickedLoc.getRow() < grid.length - 2 & clickedLoc.getCol() < grid.length
// - 2) {
// if (grid[clickedLoc.getRow() + 1][clickedLoc.getCol() + 1] == -1 * current) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// // int c = clickedLoc.getCol();
// for (int a = 1; a <= grid.length; a++) {
//
// int r = clickedLoc.getRow();
// int c = clickedLoc.getCol();
//
// if (r + a < grid.length && c + a < grid.length) {
// if (grid[r + a][c + a] == current) {
//
// tillR = r + a;
// tillC = c + a;
// turn = true;
// a = 100;
// }
// }
//
// }
//
// if (turn) {
//
// valid = true;
// int difference = Math.abs(tillR - clickedLoc.getRow());
// for (int a = 0; a < difference; a++) {
// // for (int c = clickedLoc.getCol(); c > tillC; c--) {
// grid[clickedLoc.getRow() + a][clickedLoc.getCol() + a] = current;
//
// // }
//
// }
// }
//
// }
//
// }
//
// // ****************** UP - RIGHT ***********************
//
// if (clickedLoc.getRow() > 1 & clickedLoc.getCol() < grid.length - 2) {
// if (grid[clickedLoc.getRow() - 1][clickedLoc.getCol() + 1] == current * -1) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// // int c = clickedLoc.getCol();
// for (int a = 1; a <= grid.length; a++) {
// // for (int r = clickedLoc.getRow() - 2; r > -1; r--) {
// //
// // for (int c = clickedLoc.getCol() - 2; c > -1; c--) {
//
// int r = clickedLoc.getRow();
// int c = clickedLoc.getCol();
//
// if (r - a > -1 && c + a < grid.length) {
// if (grid[r - a][c + a] == current) {
//
// tillR = r - a;
// tillC = c + a;
// turn = true;
// a = 100;
// }
//
// }
//
// }
//
// if (turn) {
//
// valid = true;
// int difference = Math.abs(tillR - clickedLoc.getRow());
// for (int a = 0; a < difference; a++) {
// // for (int c = clickedLoc.getCol(); c > tillC; c--) {
// grid[clickedLoc.getRow() - a][clickedLoc.getCol() + a] = current;
//
// // }
//
// }
// }
//
// }
// }
//
// // ****************** DOWN-LEFT ***********************
//
// if (clickedLoc.getRow() < grid.length - 2 & clickedLoc.getCol() > 1) {
// if (grid[clickedLoc.getRow() + 1][clickedLoc.getCol() - 1] == -1 * current) {
//
// boolean turn = false;
// int tillR = 0;
// int tillC = 0;
// // int c = clickedLoc.getCol();
// for (int a = 1; a <= grid.length; a++) {
//
// int r = clickedLoc.getRow();
// int c = clickedLoc.getCol();
//
// if (r + a < grid.length && clickedLoc.getCol() - a > 1) {
// if (grid[r + a][c - a] == current) {
//
// tillR = r + a;
// tillC = c - a;
// turn = true;
// a = 100;
// }
// }
//
// }
//
// if (turn) {
//
// valid = true;
// int difference = Math.abs(tillR - clickedLoc.getRow());
// for (int a = 0; a < difference; a++) {
// // for (int c = clickedLoc.getCol(); c > tillC; c--) {
// grid[clickedLoc.getRow() + a][clickedLoc.getCol() - a] = current;
//
// // }
//
// }
// }
//
// }
//
// }
//
// if (valid) {
// current = current * -1;
// System.out.print("Valid Move!");
// System.out.println(" Current is now " + current);
// } else {
// System.out.println("Invalid Move");
// }
//
// showGrid();
// valid = false;
//
// }
//
//