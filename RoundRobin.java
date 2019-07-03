import java.util.*;
import java.io.*;

class RoundRobin{

     public static void main(String args[]){

          Scanner sc = new Scanner(System.in);
          System.out.print("Enter number of processes: ");
          int processCount = sc.nextInt();

          ArrayList<Process> pool = new ArrayList<Process>();
          ArrayList<Process> poolcopy = new ArrayList<Process>();

          System.out.println("Enter burst time & arrival time for each processes: ");

          for(int i = 0; i < processCount; i++){
               System.out.println("Process " + i + ": ");
               System.out.print("Burst Time: ");
               int bt = sc.nextInt();
               pool.add(new Process(i, bt));
               poolcopy.add(new Process(i, bt));
               System.out.println();
          }

          System.out.print("Enter Quantum: ");
          int quantum = sc.nextInt();

          int clock = 0, maxclock = 200, currentIndex = 0, q = 0;
          ArrayList<Integer> exec = new ArrayList<Integer>();
          boolean jump = false, finished = false;
          while(clock < maxclock){
               if(q == quantum && clock > 0 && jump != true){
                    q = 0;
                    if(currentIndex + 1 > pool.size() - 1)
                         currentIndex = 0;
                    else
                         currentIndex++;
               }
               jump = false;
               System.out.print("\n" + clock);

               if(pool.get(currentIndex).burstTime > 0){
                    for(int i = 0; i < poolcopy.size(); i++){
                         if(poolcopy.get(i).index != currentIndex && poolcopy.get(i).burstTime != 0){
                              poolcopy.get(i).waitingTime++;
                         }
                    }
                    pool.get(currentIndex).burstTime--;
                    poolcopy.get(currentIndex).burstTime--;
                    exec.add(currentIndex);
                    System.out.print("  Process " + currentIndex + " BT: " + pool.get(currentIndex).burstTime);
                    if(pool.get(currentIndex).burstTime == 0 && q + 1 != quantum){
                         q = 0;
                         //pool.remove(currentIndex);
                         currentIndex++;
                         if(currentIndex > pool.size() - 1){
                              currentIndex = 0;
                              int p = 0;
                              while(pool.get(currentIndex).burstTime == 0){
                                   if(p > pool.size() + 1){
                                        finished = true;
                                        break;
                                   }
                                   if(currentIndex + 1 > pool.size() - 1)
                                        currentIndex = 0;
                                   else
                                        currentIndex++;
                                   p++;
                              }
                         }
                         jump = true;
                    }
               }
               if(finished == true)
                    break;
               if(jump != true)
                    q++;
               clock++;
          }

          System.out.println("\n\n----------------------------------------------------");
          System.out.println("\t \t BT \t WT \t TAT");
          System.out.println("----------------------------------------------------");
          float totalWT = 0, totalTAT = 0;
          for(int p = 0; p < poolcopy.size(); p++){
               System.out.println("Process " + p + "\t " + poolcopy.get(p).orburst +
                                                   "\t " + poolcopy.get(p).waitingTime +
                                                   "\t " + (poolcopy.get(p).waitingTime + poolcopy.get(p).burstTime));
               totalWT = totalWT + poolcopy.get(p).waitingTime;
               totalTAT = totalTAT + (poolcopy.get(p).waitingTime + poolcopy.get(p).burstTime);
          }
          System.out.println("----------------------------------------------------");
          System.out.println("Average Waiting Time: " + (totalWT / poolcopy.size()));
          System.out.println("Average Turnaround Time: " + (totalTAT / poolcopy.size()));
          System.out.println("----------------------------------------------------");
          System.out.println("----------------------------------------------------");
          System.out.println();
          for(int i = 0; i < processCount; i++){
               System.out.print("Process " + i + "\t");
               for(int x = 0; x < exec.size(); x++){
                    if(exec.get(x) == i)
                         System.out.print("#");
                    else
                         System.out.print(" ");
               }
               System.out.println();
          }
          System.out.println("\n----------------------------------------------------");


     }

     static class Process{
          int burstTime, index, waitingTime, orburst;

          Process(int i, int bt){
               index = i;
               burstTime = bt;
               orburst = burstTime;
               waitingTime = 0;
          }
     }

}
