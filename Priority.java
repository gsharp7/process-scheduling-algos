import java.util.*;
import java.io.*;

class Priority{

     public static void main(String args[]){

          Scanner sc = new Scanner(System.in);
          System.out.println("Enter number of processes: ");
          int processCount = sc.nextInt();

          Process processes[] = new Process[processCount];
          ArrayList<Process> exec = new ArrayList<Process>();

          System.out.println("Enter burst time & priority for each processes: ");

          for(int i = 0; i < processCount; i++){
               System.out.println("Process " + i + ": ");
               System.out.print("Burst Time: ");
               int bt = sc.nextInt();
               System.out.print("Priority: ");
               int pt = sc.nextInt();
               processes[i] = new Process(bt, i, pt);
               System.out.println();
          }

          /*for(int i = 0; i < processes.length; i++){
               System.out.println("Process " + i + ": BT: " + processes[i].burstTime + " PT: " + processes[i].priority);
          }
          */

          Arrays.sort(processes);
          exec(processes);

     }

     static void exec(Process processes[]){
          ArrayList<Process> exec = new ArrayList<Process>();
          System.out.println("-------------------------------------------");
          System.out.println("-------------------------------------------");
          System.out.println("PROCESS \t BT \t WT \t TAT \t Pr");
          int waitingTime = 0, totalBurst = 0;
          float totalWaiting = 0, totalTat = 0;
          for(int p = 0; p < processes.length; p++){
               System.out.println();
               System.out.print("Process " + processes[p].index + " \t " + processes[p].burstTime);
               for(int i = 0; i < p; i++){
                    waitingTime = waitingTime + processes[i].burstTime;
               }
               totalTat = totalTat + (waitingTime + processes[p].burstTime);
               System.out.print(" \t " + waitingTime + " \t " + (waitingTime + processes[p].burstTime) + " \t" + processes[p].priority);
               totalWaiting = totalWaiting + waitingTime;
               processes[p].waitingTime = waitingTime;
               waitingTime = 0;
               totalBurst = totalBurst + processes[p].burstTime;
               exec.add(processes[p]);
          }

          System.out.println();
          System.out.println("-------------------------------------------");
          System.out.println("Average Waiting time: " + (totalWaiting / processes.length));
          System.out.println("Average Turnaround time: " + (totalTat / processes.length));
          System.out.println("-------------------------------------------");

          //System.out.println("----------------------------------------");
          for(int i = 0; i < exec.size(); i++){
               System.out.print("Process " + exec.get(i).index + " ");
               for(int w = 0; w != exec.get(i).waitingTime; w++)
                    System.out.print(" ");
               for(int b = 0; b != exec.get(i).burstTime; b++){
                    System.out.print("#");
               }
               System.out.println();
          }
          System.out.println("----------------------------------------");
     }

     static class Process implements Comparable<Process>{

          int burstTime, index, waitingTime, tat, priority;

          Process(int b, int i, int p){
               burstTime = b;
               index = i;
               priority = p;
          }

          @Override
          public int compareTo(Process p){

               if(this.priority == p.priority)
                    return 0;
               else if(this.priority < p.priority)
                    return 1;
               else
                    return -1;
          }
     }

}
