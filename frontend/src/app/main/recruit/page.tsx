"use client";

import { techDataMap } from "@/data/techData";
import {
  useInfiniteQuery,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import Image from "next/image";
import {
  Fragment,
  MouseEvent,
  useCallback,
  useEffect,
  useRef,
  useState,
} from "react";
import useSearchStore, { searchState } from "@/store/searchStore";
import { Recruit } from "@/type/interface";
import { MoonLoader } from "react-spinners";
import { useMediaQuery } from "react-responsive";
import { FaBookmark, FaRegBookmark } from "react-icons/fa";
import useAuthStore, { AuthState } from "@/store/authStore";
import { Recursive } from "next/font/google";

const apiAddress = "https://spring.pickITup.online";
const baseImg = "/Images/baseCompany.jpg";
const techDataValues = Array.from(techDataMap.values());

let token = "";

export default function RecruitPage() {
  const isLoggedIn = useAuthStore((state: AuthState) => state.isLoggedIn);
  const bookmarks = useAuthStore((state) => state.bookmarks);
  const { setBookmarks } = useAuthStore();
  const keywords = useSearchStore((state: searchState) => state.keywords);
  const query = useSearchStore((state: searchState) => state.query);
  const [wrongSrcs, setWrongSrcs] = useState<boolean[]>([]);
  const bottom = useRef<HTMLDivElement>(null);
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });
  let accessToken: string | null = "";
  if (isLoggedIn) {
    accessToken = sessionStorage.getItem("accessToken");
  }
  const recruitClickHandler = async (url: string, recruitIndex: number) => {
    if (!isLoggedIn) {
      return;
    }
    try {
      const res = await fetch(
        `${apiAddress}/users/click/recruit?recruitId=${recruitIndex}`,
        {
          method: "POST",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        }
      );
      const data = await res.json();
      window.open(url, "_blank");
    } catch (error) {
      console.log(error);
    }
  }; // 공고 클릭했다는 데이터 넘기고 새 창 오픈

  const fetchRecruits = useCallback(
    async (pageParam: number) => {
      const res = await fetch(
        `${apiAddress}/recruit/search?page=${pageParam}&size=9`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            keywords: keywords,
            query: query,
          }),
        }
      );
      return res.json();
    },
    [keywords, query]
  );

  const {
    data,
    error,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
    status,
  } = useInfiniteQuery({
    queryKey: ["recruits", keywords, query],
    queryFn: ({ pageParam }) => fetchRecruits(pageParam),
    initialPageParam: 0,
    getNextPageParam: (lastPage: number, pages) => {
      return pages.length;
    },
  }); // 인피니티 스크롤 tanstack-query사용

  const imageErrorHandler = (index: number) => {
    const tmpWrongSrcs = [...wrongSrcs];
    tmpWrongSrcs[index] = true;
    setWrongSrcs(tmpWrongSrcs);
  }; //이미지에러 발생시 대체 에너지 설정

  const bookMarkHandler = (
    event: MouseEvent<HTMLDivElement>,
    recruitId: number
  ) => {
    event.stopPropagation();
    console.log("북마크 이벤트 실행");
    checkBookmark(recruitId)
      ? deleteBookMark(recruitId)
      : addBookMark(recruitId);
  }; // 북마크 추가 혹은 제거

  const addBookMark = async (recruitId: number) => {
    if (!isLoggedIn) {
      return;
    }
    try {
      const res = await fetch(
        `https://spring.pickitup.online/users/scraps/recruit?recruitId=${recruitId}`,
        {
          method: "POST",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        }
      );
      const data = await res.json();
      console.log(data);

      const scrapRes = await fetch(
        `https://spring.pickitup.online/users/scraps/recruit`,
        {
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        }
      );
      const bookmarkData = await scrapRes.json();
      setBookmarks(bookmarkData?.response);
    } catch (error) {
      console.error(error);
    }
  }; //북마크 추가 함수

  const deleteBookMark = async (recruitId: number) => {
    try {
      const res = await fetch(
        `${apiAddress}/users/scraps/recruit?recruitId=${recruitId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        }
      );
      const data = await res.json();
      console.log(data);
      if (bookmarks !== undefined) {
        setBookmarks(
          [...bookmarks].filter((bookmark) => bookmark.id != recruitId)
        );
      }
    } catch (error) {
      console.error(error);
    }
  }; //북마크 삭제 함수

  const checkBookmark = (recruitId: number) => {
    let toggle = false;
    bookmarks?.some((bookmarkedRecruit) => {
      if (bookmarkedRecruit.id === recruitId) {
        toggle = true;
        return;
      }
    });
    return toggle;
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    const onIntersect = ([entry]: IntersectionObserverEntry[]) => {
      entry.isIntersecting && fetchNextPage();
    };
    if (bottom && bottom.current) {
      observer = new IntersectionObserver(onIntersect, {
        root: null,
        rootMargin: "0px",
        threshold: 1.0,
      });
      observer.observe(bottom.current);
    }

    return () => observer && observer.disconnect();
  }, [bottom, fetchNextPage, fetchRecruits]);

  return (
    <>
      <div className="flex flex-wrap justify-center">
        {data?.pages.map((page, i: number) =>
          page.response?.content.map((recruit: Recruit, recruitI: number) => {
            return (
              <button
                type="button"
                onClick={() => recruitClickHandler(recruit.url, recruitI)}
                key={recruitI}
                className={`${isMobile ? "w-full" : "w-[30%] mx-4 max-w-72"} my-4 h-[350px] rounded-xl overflow-hidden flex flex-col shadow duration-300 ease-in hover:scale-105 relative`}
              >
                <Image
                  src={wrongSrcs[recruitI] ? baseImg : recruit.thumbnailUrl}
                  alt="thumbnail"
                  width="400"
                  height="400"
                  className={`shadow-inner shadow-black object-cover h-[50%] w-full`}
                  onError={() => imageErrorHandler(recruitI)}
                />
                <div className="flex flex-col w-full gap-3 px-4 py-5">
                  <div className="flex flex-row justify-between">
                    <div className="text-sm font-semibold text-f5gray-500">
                      {recruit.company}
                    </div>
                    <div className="text-sm font-semibold text-f5gray-500">
                      {"📆 " +
                        recruit.dueDate[0] +
                        "-" +
                        recruit.dueDate[1] +
                        "-" +
                        recruit.dueDate[2]}
                    </div>
                  </div>
                  <p className="mt-1 text-base font-bold text-left text-f5black-300 min-h-12">
                    {recruit.id}
                    {recruit.title}
                  </p>
                  {recruit.qualificationRequirements.length === 0 ? (
                    <div className="text-start text-xs text-f5black-400"> * 관련 기술스택이 없습니다 🙄 </div>
                  ) : (
                    <div className="flex flex-wrap gap-2 ml-2">
                      {recruit.qualificationRequirements.map((tech, i) => {
                        let techTmp = tech.replace(/\s/g, "");
                        techTmp = techTmp.replace(/#/g, "Sharp");

                        if (
                          techDataValues.some((techDataValueArr) =>
                            techDataValueArr.includes(techTmp)
                          )
                        )
                          return (
                            <div key={i}>
                              <Image
                                src={`/images/techLogo/${techTmp}.png`}
                                alt={tech}
                                width="100"
                                height="100"
                                className="w-auto h-8"
                              />
                            </div>
                          );
                      })}
                    </div>
                  )}
                  <div
                    onClick={(event) => bookMarkHandler(event, recruit.id)}
                    className={`text-xl absolute bottom-4 right-4 z-10 text-f5green-300 ${isLoggedIn ? "" : "hidden"} duration-300 ease-in-out hover:scale-125 `}
                  >
                    {checkBookmark(recruit.id) ? (
                      <FaBookmark />
                    ) : (
                      <FaRegBookmark />
                    )}
                  </div>
                </div>
              </button>
            );
          })
        )}
      </div>

      <div className="flex justify-center items-center h-[40vh]">
        {isFetching && isFetchingNextPage ? (
          <MoonLoader color="#36d7b7" />
        ) : null}
      </div>
      <div ref={bottom} />
    </>
  );
}
