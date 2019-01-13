#include "http_messages.hh"
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

// You could implement your logic for handling /cgi-bin requests here

HttpResponse handle_cgi_bin(const HttpRequest& request) {
  HttpResponse response;
  response.http_version = request.http_version;
  // TODO: Task 2.2

  int index = 0;
  int qMark = 0;
  bool argFlag = false;
  std::string defaultDir = "./http-root-dir";
  std::string routeDir;

  for (index = 0; index <= request.request_uri.length() - 1; index++) {
    if (request.request_uri[index] != '?') {
      // do nothing
    }
    else {
      //argument called
      argFlag = true;
      
      //Question mark index position found
      break;
    }
    //std::cout << "INDEX: " << index << std::endl;
  }

  // question mark in string argument
  if (argFlag == true) {
    std::cout << "ARG FLAG TRUE\n\n\n";
    int position = index + 1;
    std::cout << "URI: " << request.request_uri << std::endl;
    std::cout << "INDEX of ?: " << qMark << "  REQ LEN: " << request.request_uri.length() << "\n\n\n";
    std::string strArgs = request.request_uri.substr(position, request.request_uri.length() - 1);
    std::cout << "QUERY STRING: " << strArgs << "\n\n\n";
    //response.headers.insert(std::pair<std::string, std::string>("QUERY_STRING", strArgs));
    setenv("REQUEST_METHOD", "GET", 1);
    setenv("QUERY_STRING", strArgs.c_str(), 1);
  }

  // set route from http-root-dir
  routeDir = defaultDir + request.request_uri.substr(0, index);

  //std::cout << "ROUTE::: " << routeDir << std::endl;

  int fd[2];
  pipe(fd);

  int frk = fork();
  if (frk == 0) {
    close(fd[0]);
    char* argv[2];

    dup2(fd[1], 1);
    //close(fd[0]);

    argv[1] = NULL;
    
    // convert path to char* from string
    argv[0] = const_cast <char*>(routeDir.c_str());

    execvp(argv[0], argv);
    perror("ERROR: execvp failed!");
    //_exit(1);
    exit(1);

  }
  else {
    // parent, do nothing
  }
  
  // wait for child process (frk)
  waitpid(frk, NULL, 0);
  close(fd[1]);

  std::string strTotal = "";
  int readInSize = 0;
  char buffer[9000];

  while (true) {
    readInSize = read(fd[0], buffer, sizeof(buffer));

    if (2 > readInSize) {
      break; // empty
    }
    else {
      strTotal = strTotal + buffer;
    }
    
  }
  
  buffer[readInSize] = '\0';
  
  // close pipe
  close(fd[0]);

  int index2 = 0;
  int strTotalLen = strTotal.length();
  int spaceBefore = 0;
  int spaceAfter = 0;
  int spaceCount = 0;
  std::string ContentType;

  // grab content-type!!!
  while (strTotalLen - 1 >= index2) {
   
    if ((strTotal.at(index2) == ' ' || strTotal.at(index2) =='\n') && spaceCount == 1) {
      spaceCount++;
      spaceAfter = index2;
    }
 
    if (strTotal.at(index2) == ' ' && spaceCount == 0) { 
      spaceCount++;
      spaceBefore = index2;
    }
    
    if (spaceCount >= 2) {
      break;
    }

    index2++;
  }

  int ContentTypeLen = (spaceAfter - spaceBefore - 1); 

  response.status_code = 200;
  response.reason_phrase = "OK";
  response.message_body = strTotal;
  response.headers["Content-Length"] = response.message_body.length();
  ContentType = strTotal.substr(spaceBefore + 1, spaceAfter - 1);
  ContentType = ContentType.substr(0, ContentTypeLen);
  response.headers.insert(std::pair<std::string, std::string>("Content-Type", ContentType));


  return response;
}





