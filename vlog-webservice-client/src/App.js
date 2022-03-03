import GlobalStyled from "./components/styled/commonStyled/GlobalStyled";
import GlobalWrapper from "./components/styled/commonStyled/GlobalWrapper";

import Header from "./components/common/Header";
import Main from "./components/main/Main";
import SearchResult from "./components/search/SearchResult";
import Channel from "./components/channel/Channel";
import Recent from "./components/recent/Recent";
import Upload from "./components/upload/Upload";
import Error from "./components/common/Error";
import Loading from "./components/common/Loading";
import Edit from "./components/upload/Edit";
import Setting from "./components/common/Setting";
import { Route, Routes } from "react-router-dom";

import Test from "./Test";
function App() {
  return (
    <>
      <GlobalStyled />
      <div className="App">
        <GlobalWrapper>
          <Header />
          <Routes>
            <Route path="/" element={<Main />} />
            <Route path="channel/:userName/:userId" element={<Channel />} />
            <Route path="/upload" element={<Upload />} />
            <Route path="/recent" element={<Recent />} />
            <Route path="/search" element={<SearchResult />} />
            <Route path="/upload" element={<Upload />} />
            <Route path="/loading" element={<Loading />} />
            <Route path="/edit" element={<Edit />} />
            <Route path="*" element={<Error />} />
            <Route path="/setting" element={<Setting />} />
            <Route path="/test" element={<Test />} />
          </Routes>
        </GlobalWrapper>
      </div>
    </>
  );
}

export default App;
