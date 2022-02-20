import GlobalStyled from "./components/styled/commonStyled/GlobalStyled";
import GlobalWrapper from "./components/styled/commonStyled/GlobalWrapper";

import Header from "./components/common/Header";
import Main from "./components/main/Main";
import SearchResult from "./components/search/SearchResult";
import Channel from "./components/channel/Channel";
import Recent from "./components/recent/Recent";
import Upload from "./components/upload/Upload";
import Error from "./components/common/Error";
import VideoModal from "./components/videoModal/VideoModal";
import { Route, Routes } from "react-router-dom";
import { Provider } from "react-redux";
import { store } from "./redux/store";
import Test from "./Test";

function App() {
  return (
    <>
      <GlobalStyled />
      <div className="App">
        <Provider store={store}>
          <GlobalWrapper>
            <Header></Header>
            <Routes>
              <Route path="/" element={<Main />} />
              <Route path="channel/:userName" element={<Channel />} />
              <Route path="/upload" element={<Upload />} />
              <Route path="/recent" element={<Recent />} />
              <Route path="/search" element={<SearchResult />} />
              <Route path="/upload" element={<Upload />} />
              <Route path="*" element={<Error />} />
              <Route path="/loading" element={<Test />} />
            </Routes>
          </GlobalWrapper>
        </Provider>
      </div>
    </>
  );
}

export default App;
