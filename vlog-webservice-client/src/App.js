import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";


function App() {
  const [message, setMessage] = useState([]);

  useEffect(() => {
    fetch("api/hello")
        .then((response) => {
          return response.json();
        })
        .then(function (data) {
          setMessage(data);
        });

  },[]);

  return (
    <div className="App">
        <form method="post" action="http://localhost:8080/api/v1/posts" encType="multipart/form-data">
            <input multiple type="file" name="video"/>

            <input type="text" name="userId"/>
                <input type="text" name="description"/>
                    <input type="text" name="tags"/>

                        <button>submit</button>
        </form>
    </div>
  );
}

export default App;
