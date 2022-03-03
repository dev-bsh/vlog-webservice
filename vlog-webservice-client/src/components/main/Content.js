import React, { useEffect, useState, useRef } from "react";
import { useSelector, useDispatch } from "react-redux";
import { fetchPost, fetchPostPopular } from "../../redux/module/post";
import Post from "./Post";
import Loader from "./Loader";
import TargetStyled from "../styled/mainStyled/TargetStyled";
import PostWrapperStyled from "../styled/mainStyled/PostWrapperStyled";
import axios from "axios";

const Content = () => {
  const [target, setTarget] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [itemLists, setItemLists] = useState([]);
  const [isEnd, setIsEnd] = useState(false);
  const [pageNum, setPageNum] = useState(0);
  const pageEnd = useRef();

  const getMoreItem = async () => {
    const response = await axios.get(`api/v1/posts/${pageNum}/popular`);
    let items = response.data;
    setItemLists((itemLists) => itemLists.concat(items));
    setIsLoaded(true);
  };

  const loadMore = () => {
    setPageNum((prevPageNum) => prevPageNum + 1);
  };

  useEffect(() => {
    getMoreItem(pageNum);
  }, [pageNum]);

  useEffect(async () => {
    if (isLoaded) {
      const observer = await new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting) {
            loadMore();
          }
        },
        { threshold: 1 }
      );
      observer.observe(pageEnd.current);
    }
  }, [isLoaded]);

  return (
    <>
      <PostWrapperStyled>
        {itemLists.map((post, i) => {
          return <Post post={post} key={i} />;
        })}
      </PostWrapperStyled>
      {isEnd && <div>마지막 게시물입니다.</div>}
      {!isEnd && (
        <TargetStyled ref={pageEnd}>{isLoaded && <Loader />}</TargetStyled>
      )}
    </>
  );
};

export default Content;
