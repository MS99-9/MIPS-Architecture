# MIPS Architecture
  The MIPS32 instruction set is an instruction set standard published in 1999. Currently, the MIP32 instruction set is the most common MIPS instruction set, compatible with most CPUs. Due to its relative simplicity, it is also the most common instruction set taught in computer architecture university courses. In this project, I will implement a simplified version of the MIPS32 Architecture using Java as a programming language.

  1 Instruction Set Architecture
  The Instruction Set Architecture (ISA) contains the registers, instructions, and their formats. I implemented all features described in this section.
  1.1 Registers
  The register size is 32 bits.
  • All data will be 32 bits in size
  My implementation contains the following 13 registers:
  a) 6 Saved Registers: $s0,$s1,$s2,$s3,$s4,$s5
  b) 6 Temporary Registers: $t0,$t1,$t2,$t3,$t4,$t5
  c) Zero Register: $zero
  • The zero register contains the value “0”, and no other instruction can overwrite the content of this register.
  2
  1.2 Instruction Formats
  The instruction size is 32 bits.
  My implementation supports the following 3 instruction formats:
  a) R-Format: opcode
  6 bits b) I-Format:
  opcode
  6 bits
  c) J-Format:
  rs
  5 bits
  rs
  5 bits
  rt
  5 bits
  rt
  5 bits
  rd shamt function
    5 bits 5 bits
  immediate/offset
  16 bits
  6 bits
       opcode address
  6 bits 26 bits
  1.3 Instruction List
    My implementation includes the following 10 instructions: a) R-Type: 5 instructions
  1. Add: add $rd, $rs, $rt
  2. Subtract: sub $rd, $rs, $rt
  3. Or: or $rd, $rs, $rt
  4. Nor: nor $rd, $rs, $rt
  5. Set Less Than: slt $rd, $rs, $rt
  b) I-Type: 4 instructions
  1. Add Immediate: addi $rt, $rs, immediate 2. Load Word: lw $rt, offset($rs)
  3. Store Word: sw $rt, offset($rs)
  4. Branch on Equal: beq $rs, $rt, offset
  • The Branch on Equal instruction takes an offset and not an address to jump to. The offset is added to the Program Counter (PC) as you will see later.
  c) J-Type: 1 instruction 1. Jump: j address
  3

  2 Data path
  I implemented the MIPS32 pipelined version. In order to do so, I needed to implement the basic single-cycle data path, then modify it to support pipelining.
  Stages
  My data path implementation supports the 5-stage instruction cycle present in the MIPS32 architecture .
   The MIPS32 single-cycle implementation reflecting 5 stages
  a) Instruction Fetch (IF): Fetches the next instruction from memory using the address in the PC (Program Counter)
  b) Instruction Decode (ID): Decodes the instruction, calculates the next PC, and reads any ope- rands required from the register file
  c) Execute (EX): Executes the instruction. In fact, all ALU operations are done in this stage
  d) Memory (MEM): Performs any memory access required by the current instruction. For loads, it
  would load an operand from memory, while for stores, it would store an operand into memory
  e) Write Back (WB): For instructions that have a result (a destination register), the Write Back writes this result back to the register file
   4

  2.1 Non-Pipelined Data path
  My implementation includes all the components and elements present in the single-cycle (non- piplined) data path . Any instruction must pass through the correct path in order to get executed.
  The MIPS32 non-pipelined data path
  a) Program Counter (PC): Contains the address of the instruction to be executed, then is updated to point to the next instruction
  b) 1024 x 32 Instruction Memory (IM): Contains the program assembly code
  • Since MIPS32 is based on the Von Neumann architecture, the Instruction Memory
  acts as the Instruction Cache
  c) Register File: Contains the values of all the registers
  d) Arithmetic Logic Unit (ALU): Performs arithmetic operations
  e) 1024 x 32 Data Memory (DM): Contains the data stored in the memory
  • Since MIPS32 is based on the Von Neumann architecture, the Data Memory acts as the Data Cache
  f) Control Unit (CU): Outputs the signals needed by the data path components
  g) ALU Control: Informs the ALU about the operation to be performed
  h) Sign-Extend Module: Extends a 16-bit signed number into a 32-bit signed number
  i) Shift Left Unit: Shifts 2 bits to the left
  j) And Gate: Anding of 2 bits
  k) All Multiplexers: Selects one of the two inputs to pass to the next stage
  l) All Addition Units (Add): Performs an addition between the input parameters
   5

  2.2 Pipelined Data path
  My implementation supports the pipelined version of MIPS32. I implemented the four pipeline registers found between the stages.
  The MIPS32 pipelined data path missing some elements
  I need to identify which data and signals to store in each of the following pipe- line registers:
  a) IF/ID: Stores data from the Instruction Fetch stage
  b) ID/EX: Stores data and signals from the Instruction Decode stage
  c) EX/MEM: Stores data and signals from the Execution stage d) MEM/WB: Stores data and signals from the Memory stage
  I made sure that the data and signals retrieved in my implementation are retrieved from the correct sources.
   6

  3 Guidelines
  The following testing guidelines must followed:
  3.1 Program Flow
  a) I wrote my program in MIPS32 Assembly language in a text file
  b) My implementation must parse/translate the Assembly instructions to their corresponding 32-
  bit binary format according to the Instruction Formats in Section 1.2
  c) The 32-bit binary format instructions must be stored in the Instruction Memory.
  d) My program is at Clock Cycle 0 initially.
  e) I should start the execution of my pipelined implementation by fetching the first instruction from the Instruction Memory at Clock Cycle 1
  f) At Clock Cycle 2:
  • Instruction Fetch: Instruction #2
  • Instruction Decode: Instruction #1 • Execute: N/A
  • Memory: N/A
  • Write Back: N/A
  g) At Clock Cycle 3:
  • Instruction Fetch: Instruction #3
  • Instruction Decode: Instruction #2 • Execute: Instruction #1
  • Memory: N/A
  • Write Back: N/A
  h) At Clock Cycle 4:
  • Instruction Fetch: Instruction #4
  • Instruction Decode: Instruction #3 • Execute: Instruction #2
  • Memory: Instruction #1
  • Write Back: N/A
  i) At Clock Cycle 5:
  • Instruction Fetch: Instruction #5
  • Instruction Decode: Instruction #4 • Execute: Instruction #3
  • Memory: Instruction #2
  • Write Back: Instruction #1
  j) At Clock Cycle 6:
  • Instruction Fetch: Instruction #6
  • Instruction Decode: Instruction #5 • Execute: Instruction #4
  • Memory: Instruction #3
  • Write Back: Instruction #2
  and so on for the rest of the program....
  7

  3.2 Printings
  The following items must be printed in the console after each Clock Cycle:
  a) The Clock Cycle number
  b) The 5 Pipeline stages:
  • Which instruction is being executed at each stage?
  • What are the input parameters/values for each stage?
  c) The content of the Saved, Temporary, and Zero registers after executing this clock cycle
  d) The content of the Instruction Memory
  e) The content of the Data Memory after executing this clock cycle
  f) The content of the 4 Pipeline Registers after executing this clock cycle
  3.3 Bonus
  a) A graphical user interface (GUI) will be considered as a bonus
  b) A user can write the Assembly program in the graphical user interface, and start executing the program cycle by cycle
  c) The graphical user interface should perform and reflect all the functionalities stated in Section 3.2
