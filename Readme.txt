FEM (FILE ENCRYPTION MODULE)

--------------------------------------------------------------------------------

Created by Spencer Gang

GOAL: Create a desktop application that incorporates AES encryption to encrypt files of ANY type

USES:
    -Encryption: Browse and select files of any type to encrypt. Given password and output file name is user choice.
    -Decryption: Browse and select encrypted files, decrypt given file with password given on encryption, output file name is user choice
    -NOTE: WHEN DECRYPTING FILES YOU MUST APPEND FILE EXTENSION MANUALLY
    
HOW IT WORKS:
  FEM, as explained above, works to encrypt files with the Advanced Encryption Standard (AES) algorithm. Before any encryption occurs
  files are read through input buffers for binary data, this data is then manipulated by Java's builtin Crypto functions. With typical 
  AES encryption passwords must be multiples of 16 bytes, FEM uses AES/CBC/PKCS5Padding on our password to ensure that our password
  falls within a multiple of 16 no matter password length. Decryption follows the same logic, input buffers read files while Crypto 
  functions decrypt the files binary data. On naming of decrypted files it is necessary that you append the correct file type to the
  output name. Files are always output in the same directory the input file is found.
  
Final Version Release Date: 12-3-2016
