/**
 * This file contains the primary logic for your server. It is responsible for
 * handling socket communication - parsing HTTP requests and sending HTTP responses
 * to the client. 
 */

#include <functional>
#include <iostream>
#include <sstream>
#include <vector>
#include <tuple>

#include <string.h>
#include <fstream>
#include <sys/stat.h>
#include <unistd.h>
#include <thread>
#include <pthread.h>
#include <sys/types.h>
#include <dirent.h>
#include <experimental/filesystem>
#include <string>

#include "server.hh"
#include "http_messages.hh"
#include "errors.hh"
#include "misc.hh"
#include "routes.hh"


namespace fs = std::experimental::filesystem;

Server::Server(SocketAcceptor const& acceptor) : _acceptor(acceptor) { }

void Server::run_linear() const {
  while (1) {
    Socket_t sock = _acceptor.accept_connection();
    handle(sock);
  }
}

void Server::run_fork() const {
  // TODO: Task 1.4
  while (1) {
    Socket_t sock = _acceptor.accept_connection();
    int frk = fork();
    if (frk == 0) {
      // forked
    }
    else {
      handle(sock);
      exit(0);
    }
  }
}

void Server::run_thread() const {
  // TODO: Task 1.4
  while (1) {
    // accept connection(s)
    Socket_t sock = _acceptor.accept_connection();

    // thread of execution (initialized thread object) 
    std::thread newThread(&Server::handle, this, std::move(sock));

    // make non-joinable
    newThread.detach();
  }


//    std::thread(&Server::handle, this, std::move(sock)).detach();
//  }

}

void Server::run_thread_pool(const int num_threads) const {
  // TODO: Task 1.4

  int threadNum = 0;

  for (threadNum = 0; threadNum <= num_threads - 1; threadNum++) {

    std::thread newThread(&Server::run_linear, this);
    newThread.detach();

//    std::thread(&Server::run_linear, this).detach();
  }

}

// example route map. you could loop through these routes and find the first route which
// matches the prefix and call the corresponding handler. You are free to implement
// the different routes however you please
/*
std::vector<Route_t> route_map = {
  std::make_pair("/cgi-bin", handle_cgi_bin),
  std::make_pair("/", handle_htdocs),
  std::make_pair("", handle_default)
};
*/

void Server::handle(const Socket_t& sock) const {
  HttpRequest request;
  HttpResponse resp;

  // TODO: implement parsing HTTP requests
  //--------------------------------------------------------------------------------

  
  int req = 0; // zero when first line and positive otherwise
  std::string file, version;
  std::string authStr;

  while (1) {


    // read line
    std::string request_line = sock->readline();


    // exit if enter typed twice
    if (request_line.size() == 2) {
      break;
    }


    // First request, always the same format (METHOD, FILE, HTTP VERSION)
    std::string key, val;
    if (req == 0) {
      typedef std::map<std::string, std::string> reqMap;
      std::istringstream iss(request_line);

      // store as key and val
      reqMap m;
      while(std::getline(std::getline(iss, key, ' ') >> std::ws, val)) {
        m[key] = val;
      }

      // Check to see if stored in map correctly
      reqMap::iterator pos;
      for (pos = m.begin(); pos != m.end(); ++pos) {
       //std::cout << "MAP KEY: " << pos->first << " VAL: " << pos->second << std::endl;
       request.method = pos->first;
      }

      // Get file name
      std::stringstream ss(val);
      ss >> file;

      // Set request_uri
      request.request_uri = file;

      // Get HTTP version
      ss >> version;

      // Set request http_version
      request.http_version = version;

      req = 1;
    }
    else if (req == 1) {

      typedef std::map<std::string, std::string> reqMap;
      std::istringstream iss(request_line);

      // store as key and val
      reqMap m;
      while(std::getline(std::getline(iss, key, ' ') >> std::ws, val)) {
        m[key] = val;
      }

      // Check to see if stored in map correctly
      reqMap::iterator pos;
      for (pos = m.begin(); pos != m.end(); ++pos) {
        //std::cout << "MAP KEY: " << pos->first << " VAL: " << pos->second << std::endl;
        key = pos->first;
        val = pos->second;
        authStr = pos->second;
      }

//      while (request_line.compare("\r\n") != 0) {

//      }

      
//      std::string Content_Type;
//      Content_Type = get_content_type(std::string(fileRoute.c_str()));
//      std::cout << "CONTENT: " <<  Content_Type << std::endl;
      if (key == "Authorization:") {
        //std::cout << "AUTH KEY INPUT" << std::endl;

	std::string delimiter = " ";
	std::string basic = authStr.substr(0, authStr.find(delimiter));
        delimiter = '\0';
	std::string authToken = authStr.substr(6, authStr.find(delimiter));

        request.headers.insert(std::pair<std::string, std::string>("Authorization", authToken));

//        std::cout << "PRINT AUTH: " << request.headers.at("Authorization") << std::endl;
      }
    }


  }
  //--------------------------------------------------------------------------------
  // Grab request line
/*
  std::string request_line = sock->readline();
  std::string authentication_line = sock->readline();

//  while (request_line.compare("\r\n") != 0) {
//
//  }

  // Print Request line string
  std::cout << "Request Line: " << request_line << std::endl;
  // Parse request line into key and value for map
  typedef std::map<std::string, std::string> reqMap;
  std::string key, val;
  std::istringstream iss(request_line);

  // Store as key and value in map
  reqMap m;
  while(std::getline(std::getline(iss, key, ' ') >> std::ws, val)) {
    m[key] = val;
  }

  // Check to see if stored in map correctly
  reqMap::iterator pos;
  for (pos = m.begin(); pos != m.end(); ++pos) {
    //std::cout << "MAP KEY: " << pos->first << " VAL: " << pos->second << std::endl;
    request.method = pos->first;
  }

  // Split value accordingly
  std::string file;
  std::string version;

  // Get file name
  std::stringstream ss(val);
  ss >> file;

  // Set request_uri
  request.request_uri = file;

  // Get HTTP version
  ss >> version;
  
  // Set request http_version
  request.http_version = version;
*/

  std::string cgiDir = file.substr(0,8);
  std::string fileDir;

  // divert all processing to cgi-bin.cc file
  if (!cgiDir.compare("/cgi-bin")) {
    // call function to handle cgi
    HttpResponse respCgi = handle_cgi_bin(request);

    // handle responses
    request.print();
    //std::cout << "RESP CGI: " << respCgi.to_string() << "\n\n";
    sock->write(respCgi.to_string());

  }
  // set path if not /cgi-bin and carry on 
  else {
    fileDir = "./http-root-dir/htdocs";
  

  // check if file or directory and append accordingly
  char lastCh = file.back();


  // append file name to directory
  std::ostringstream oss;
  oss << fileDir << file;

  std::string fileRoute = oss.str();
  bool dirFlag = false;

  struct stat s;
  if (stat(fileRoute.c_str(), &s) == 0) {
    if (s.st_mode & S_IFDIR) {
      //dir
      if (lastCh == '/') {
/*
        std::cout << "SLASH \n\n";
        std::ostringstream oss;
        std::string defaultFile = "index.html";
        oss << fileRoute << defaultFile;
        fileRoute = oss.str();
*/
        // Implement 2.5 (Browsable Directories)
	dirFlag = true;
        auto const dir_path = fs::path(fileRoute);
        std::vector<std::string> routes;
	//std::ostringstream oss2;

	// header and title
	resp.message_body = "<!DOCTYPE html> <html> <head> <h3>Browsable Directories</h3> </head>";

	for (auto const & elem : fs::directory_iterator(dir_path)) {
          //resets file links! must be in here! (ostringstream oss2)
          std::ostringstream oss2;
	  oss2 << elem;
	  std::string element;
          element = oss2.str();
          routes.push_back(element);
        }

        // build directory list for browser
        // body start (unordered list)
        resp.message_body = resp.message_body + "<body> <ul>";

	// loop for routes
	int routeNum = 0;
	while (routeNum <= routes.size() - 1) {
	  resp.message_body = resp.message_body + ("<li><a href=" + routes.at(routeNum) + "\">" + routes.at(routeNum) + "</a></li>");
	  routeNum++;
	}

	// close all html tags
        resp.message_body = resp.message_body + "</ul> </body> </html>";

	// manually set content type
        resp.headers.insert(std::pair<std::string, std::string>("Content-Type", "text/html"));

	// temp fix set all req and resp
//	request.print();
//	sock->write(resp.to_string());

      }
      else {
        //std::cout << "NO SLASH \n\n";
        std::ostringstream oss;
        std::string defaultFile = "/index.html";
        oss << fileRoute << defaultFile;
        fileRoute = oss.str();
      }
      

    }
    else if (s.st_mode & S_IFREG) {
      //file
    }
  }
  else {
    //err
    //std::cout << "ERROR: FILE/DIR not determined! \n\n";

  }
  std::cout.flush();

// temp fix to browsable directory failure (use if to determine that its a dir with ending '/')
//if (dirFlag == false) {
  // Open file and copy contents
  std::ifstream load(fileRoute.c_str());
  std::string msg;
  std::string content;
  int index = 0;

  std::string Content_Type;
 
  if (load.fail()) {
    // send status_code 404 for file not found
    resp.status_code = 404;
    resp.reason_phrase = "Not Found";
  }
  else {

    std::string authFile = "./src/auth.txt";
    std::ifstream auth(authFile);
    std::string authPass;
    int authPassLen;
    std::string authAttempt;
    std::getline(auth, authPass);    
    //std::cout << "CREDENTIALS: " << authPass << std::endl;

    authPassLen = authPass.length();

    if (request.headers.find("Authorization") != request.headers.end()) {
      authAttempt = request.headers.at("Authorization");
      //std::cout << "FIND AUTH ATTEMPT: " << authAttempt << std::endl;
      authAttempt = authAttempt.substr(0, authPassLen);
      //if (request.headers.at("Authorization") == authPass) {

      //std::cout << "FIND AUTH ATTEMPT 2: " << authAttempt << std::endl;
      if (!authAttempt.compare(authPass)) { 
        //std::cout << "THEY EQUAL EACH OTHER!!!!\n\n"; 
        while (std::getline(load, content)) {
          msg += content;
          msg.push_back('\n');
        }
      
        // Set status_code 
        resp.status_code = 200;
        resp.reason_phrase = "OK";

        // set content type
        Content_Type = get_content_type(std::string(fileRoute.c_str()));
        //std::cout << "CONTENT: " <<  Content_Type << std::endl;
	request.headers.insert(std::pair<std::string, std::string>("Content-Type", Content_Type));

      }
      else {
        // Set status_code 
        resp.status_code = 401;
        resp.reason_phrase = "Unauthorized";
      }

    }
    else {
      resp.status_code = 401;
      resp.reason_phrase = "Unauthorized";
    }

  }
  
  
/*
  else {
   
      while (std::getline(load, content)) {
        msg += content;
        msg.push_back('\n');
      }

      // Set status_code
      resp.status_code = 200;
      resp.reason_phrase = "OK";

//    Content_Type = get_content_type(std::string(fileRoute.c_str()));
//    std::cout << "CONTENT: " <<  Content_Type << std::endl;
//    request.headers.insert(std::pair<std::string, std::string>("Content-Type", Content_Type));
     
  }
*/

  request.message_body = msg;
  request.print();

 //HttpResponse resp;
  // TODO: Make a response for the HTTP request
  resp.http_version = version;

  if (dirFlag == false) {  
    resp.headers.insert(std::pair<std::string, std::string>("Content-Type", Content_Type));
  }

  resp.headers["connection"] = "close";

  if (dirFlag == false) {
    resp.message_body = msg;
    resp.headers["Content-Length"] = resp.message_body.length();
  }

  std::cout << resp.to_string() << std::endl;
  sock->write(resp.to_string());

  } // END ELSE FOR FILE ROUTING ------------------------------------------------------------------

//}
}

