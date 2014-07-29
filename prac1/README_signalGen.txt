Author: Benjamin Hugo

******************************************
SIGNAL GENERATION (PYTHON + NUMPY)
******************************************
You must have the SCILAB suite of packages installed. This includes numpy, scipy signals and matplotlab

To generate a fake trasmission signal and noisy received signal hit the following:
python radar_signal_gen.py transmit.txt receive.txt

This will additionally give you the results of a serial implementation and a few plots

You may tinker with the number of samples if you need more samples.

WARNING: it will take a while to correlate a large number of samples!!! Be patient!!!
