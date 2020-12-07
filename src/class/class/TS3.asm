; TS3.asm - Test file for Sprint 3 release
Main
      lda.i16     Msg
      trap        133      ; puts
      lda.i16     Msg2
      trap        133      ; puts
Fct   ldc.i3      2        
      ret
      call.i16   Fct
      halt
Msg   .cstring    "A2"     ; OK. Code generated -> 41 32 00
Msg2  .cstring    "B25"    ; Only ASCII printable characters are allowed.