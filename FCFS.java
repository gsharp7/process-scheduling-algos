import java.util.*;
import java.io.*;

class FCFS{

     public static void main(String args[]){

          Scanner sc = new Scanner(System.in);
          System.out.println("Enter number of processes: ");
          int processCount = sc.nextInt();

          Process processes[] = new Process[processCount];

          List<Process> plist = new ArrayList<Process>();

          System.out.println("Enter burst time for each processes: ");

          for(int i = 0; i < processCount; i++){
               System.out.println("Process " + i + ": ");
               Process tmp = new Process(sc.nextInt(), i);
               processes[i] = tmp;
               plist.add(tmp);
          }
          System.out.println("\nFCFS\n");
          exec(processes);
          Arrays.sort(processes);
          System.out.println("\nSJF\n");
          exec(processes);



     }

     static void exec(Process processes[]){
          ArrayList<Process> exec = new ArrayList<Process>();
          System.out.println("----------------------------------------");
          System.out.println("----------------------------------------");
          System.out.println("PROCESS \t BT \t WT \t TAT ");
          int waitingTime = 0, totalBurst = 0;
          float totalWaiting = 0, totalTat = 0;
          for(int p = 0; p < processes.length; p++){
               System.out.println();
               System.out.print("Process " + processes[p].index + " \t " + processes[p].burstTime);
               for(int i = 0; i < p; i++){
                    waitingTime = waitingTime + processes[i].burstTime;
               }
               totalTat = totalTat + (waitingTime + processes[p].burstTime);
               System.out.print(" \t " + waitingTime + " \t " + (waitingTime + processes[p].burstTime));
               totalWaiting = totalWaiting + waitingTime;
               processes[p].waitingTime = waitingTime;
               waitingTime = 0;
               totalBurst = totalBurst + processes[p].burstTime;
               exec.add(processes[p]);
          }

          System.out.println();
          System.out.println("----------------------------------------");
          System.out.println("Average Waiting time: " + (totalWaiting / processes.length));
          System.out.println("Average Turnaround time: " + (totalTat / processes.length));
          System.out.println("----------------------------------------");

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
          int burstTime;
          int index;
          int waitingTime, tat;

          Process(int b, int i){
               burstTime = b;
               index = i;
          }

          @Override
          public int compareTo(Process p){

               if(this.burstTime == p.burstTime)
                    return 0;
               else if(this.burstTime < p.burstTime)
                    return -1;
               else
                    return 1;
          }
     }


}
