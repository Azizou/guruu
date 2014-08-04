#PBS -q UCTlong
#PBS -l nodes=2:ppn=4:series400
#PBS -N MyRunTest-A15
cd prac1
mpirun -hostfile $PBS_NODEFILE /opt/exp_soft/softwareX/xyz -o /home/ogbazi001/prac1/myfile.txt
