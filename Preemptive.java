import java.util.*;
import java.io.*;

class Preemptive{

     public static void main(String args[]){

          Scanner sc = new Scanner(System.in);
          System.out.print("Enter number of processes: ");
          int processCount = sc.nextInt();

          ArrayList<Process> pool = new ArrayList<Process>(),  poolcopy = new ArrayList<Process>();

          System.out.println("Enter burst time & arrival time for each processes: ");

          for(int i = 0; i < processCount; i++){
               System.out.println("Process " + i + ": ");
               System.out.print("Burst Time: ");
               int bt = sc.nextInt();
               System.out.print("Arrival Time: ");
               int at = sc.nextInt();
               pool.add(new Process(i, bt, at));
               poolcopy.add(new Process(i, bt, at));
               System.out.println();
          }

          Collections.sort(pool);
          Collections.sort(poolcopy);
          /*for(int i = 0; i < processes.length; i++){
               System.out.println("Process " + i + ": BT: " + processes[i].burstTime + " AT: " + processes[i].arrivalTime);
          }
          */

          boolean cpuExec = true;
          int clock = 0, maxclock = 30;
          Process currentProcess = null;
          ArrayList<Process> waiting = new ArrayList<Process>();
          ArrayList<Integer> exec = new ArrayList<Integer>();

          //execute processes
          while(clock <= maxclock){

               if(currentProcess != null){
                    if(currentProcess.burstTime > 0)
                         currentProcess.burstTime -= 1;

                    if(currentProcess.burstTime == 0){
                         //removeProcess(index, pool);
                         currentProcess = null;
                    }
               }

               ArrayList<Process> newp = pollProcess(clock, pool);
               int minnew = 0;
               if(newp.size() > 0){
                    pool.remove(newp);

                    for(int n = 0, min = newp.get(0).burstTime; n < newp.size(); n++){
                         if(newp.get(n).burstTime <= min){
                              minnew = n;
                              min = newp.get(n).burstTime;
                         }
                    }
                    for(int n = 0; n < newp.size(); n++){
                         if(n != minnew)
                              waiting.add(newp.get(n));
                    }

               }

               if(newp.size() > 0 && currentProcess != null){
                    //There is a new process arriving and there is a current process

                    if(newp.get(minnew).burstTime < currentProcess.burstTime ){
                         waiting.add(currentProcess);
                         currentProcess = newp.get(minnew);
                    }
                    else
                         waiting.add(newp.get(minnew));
               }
               else if(newp.size() > 0 && currentProcess == null){
                    //There is a new process arriving and there is no current process
                    if(waiting.size() > 0){
                         //search for minimum burst in waiting list
                         int minidx = 0;
                         for (int i = 0, min = waiting.get(0).burstTime; i < waiting.size(); i++) {
                              if(waiting.get(i).burstTime <= min){
                                   minidx = i;
                                   min = waiting.get(i).burstTime;
                              }
                         }
                         if(newp.get(minnew).burstTime < waiting.get(minidx).burstTime){
                              currentProcess = newp.get(minnew);
                         }
                         else{
                              currentProcess = waiting.get(minidx);
                              waiting.remove(minidx);
                              waiting.add(newp.get(minnew));
                         }
                    }
                    else{
                         currentProcess = newp.get(minnew);
                    }

               }

               if(currentProcess == null && waiting.size() > 0){
                    int minidx = 0;
                    for (int i = 0, min = waiting.get(0).burstTime; i < waiting.size(); i++) {
                         if(waiting.get(i).burstTime <= min){
                              minidx = i;
                              min = waiting.get(i).burstTime;
                         }
                    }
                    currentProcess = waiting.get(minidx);
                    waiting.remove(minidx);
               }

               //waiting = incrWaitingTime(waiting);
               if(currentProcess == null){
                    System.out.println(clock);
                    exec.add(-1);
               }
               else{
                    System.out.println(clock + "\t Process " + currentProcess.index);
                    exec.add(currentProcess.index);
                    //record waiting time for each process
                    for(int px = 0; px < poolcopy.size(); px++){
                         if(poolcopy.get(px).index != currentProcess.index && clock >= poolcopy.get(px).arrivalTime
                         )
                              poolcopy.get(px).waitingTime++;
                    }
               }

               clock++;
          }

          System.out.println("----------------------------------------------------");
          System.out.println("\t \t AT \t BT \t WT \t TAT");
          System.out.println("----------------------------------------------------");
          float totalWT = 0, totalTAT = 0;
          for(int p = 0; p < poolcopy.size(); p++){
               System.out.println("Process " + p + "\t " + poolcopy.get(p).arrivalTime +
                                                   "\t " + poolcopy.get(p).burstTime +
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



     static ArrayList<Process> pollProcess(int clockTick, ArrayList<Process> pool){
          ArrayList<Process> a = new ArrayList<Process>();
          for(int i = 0; i < pool.size(); i++){
               if(pool.get(i).arrivalTime == clockTick){
                    a.add(pool.get(i));
               }
          }
          return a;
     }

     static ArrayList<Process> incrWaitingTime(ArrayList<Process> waiting){
          for(int i = 0; i < waiting.size(); i++)
               waiting.get(i).waitingTime++;
          return waiting;
     }

     static class Process implements Comparable<Process>{
          int burstTime, arrivalTime, index, waitingTime;
          boolean started;

          Process(int i, int bt, int at){
               index = i;
               burstTime = bt;
               arrivalTime = at;
               waitingTime = 0;
               started = false;
          }

          @Override
          public int compareTo(Process p){

               if(this.arrivalTime == p.arrivalTime)
                    return 0;
               else if(this.arrivalTime < p.arrivalTime)
                    return -1;
               else
                    return 1;
          }
     }
}
