# SOEN341 - PROJECT B
Cross Assembler for Cm Virtual Machine

### Team B6
* Kleard Mama
* David Seleznev
* Kevin de Oliveira
* Titus Poenaru
* Tiago Strobino
* Ismael Rekik

### Progress
- [ ] Sprint1
- [ ] Sprint2
- [ ] Sprint3
- [ ] Sprint4

---

### Release 1 (for sprint 1)
all items (*) are prioritized. The team has the duty to estimate the size (poker game).
* generate and print an assembly listing (for mnemonic only - inherent addressing mode)
* parse an AssemblyUnit (a sequence of nodes)
* parse a LineStatement
* parse an Instruction (for mnemonic only - inherent addressing mode)
* parse an InherentInstruction
* create a (class) Node
* extract tokens with a lexer (Lexical Analyser or Scanner) with the functionalities:
  * scan an identifier (not label yet, only mnemonic)
  * get a token
* enter and lookup nodes in SymbolTable
* record and report errors in ErrorReporter
* keep line number and column number in Position
* save error messages and position in ErrorMessage
* keep filename and extension in SourceFile

---
