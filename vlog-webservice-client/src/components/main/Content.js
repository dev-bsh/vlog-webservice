import React, { useEffect, useState, useMemo } from "react";
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
  const [lastId, setLastId] = useState("");

  const dispatch = useDispatch();
  const posts = useSelector((state) => state.post.posts);
  let last;

  const getMoreItem = async (last, items) => {
    setIsLoaded(true);
    await new Promise((resolve) => setTimeout(resolve, 500));

    await setItemLists((itemLists) => itemLists.concat(items));

    /* 중복되는 아이템이 리스트에 추가되는 버그존재*/
    setIsLoaded(false);
  };

  const onIntersect = async ([entry], observer) => {
    if (entry.isIntersecting && !isLoaded) {
      observer.unobserve(entry.target);
      try {
        const response = await axios.get(
          `http://localhost:8080/api/v1/posts/${last}/popular`
        );
        last = await response.data[response.data.length - 1].postsId;
        let items = response.data;
        await getMoreItem(last, items);
      } catch {
        setIsEnd(true);
        console.log("end");
      }
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    axios.get("api/v1/posts/popular").then((response) => {
      setItemLists(response.data);
    });
  }, []);
  useEffect(async () => {
    let observer;

    const res = await axios.get("http://localhost:8080/api/v1/posts/popular");
    last = await res.data[res.data.length - 1].postsId;

    if (target && !isLoaded) {
      observer = new IntersectionObserver(onIntersect, {
        threshold: 0.5,
      });
      observer.observe(target);
    }
    return () => observer && observer.disconnect();
  }, [target]);

  return (
    <>
      <PostWrapperStyled>
        {itemLists.map((post, i) => {
          return <Post post={post} key={i} />;
        })}
      </PostWrapperStyled>
      {isEnd && <div>마지막 게시물입니다.</div>}
      {!isEnd && (
        <TargetStyled ref={setTarget}>{isLoaded && <Loader />}</TargetStyled>
      )}
    </>
  );
};

export default Content;
