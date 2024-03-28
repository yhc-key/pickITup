"use client";

import { techDataMap } from "@/data/techData";
import {
  useInfiniteQuery,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";
import Image from "next/image";
import { Fragment, useCallback, useEffect, useRef, useState } from "react";
import useSearchStore, { searchState } from "@/store/searchStore";
import { Recruit } from "@/type/interface";
import { MoonLoader } from "react-spinners";
import { useMediaQuery } from "react-responsive";

const apiAddress = "https://spring.pickITup.online/recruit";
const baseImg = "/Images/baseCompany.jpg";
const techDataValues = Array.from(techDataMap.values());
const recruitClickHandler = (url: string) => {
  window.open(url, "_blank");
};

export default function RecruitPage() {
  const keywords = useSearchStore((state: searchState) => state.keywords);
  const query = useSearchStore((state: searchState) => state.query);
  const [wrongSrcs, setWrongSrcs] = useState<boolean[]>([]);
  const bottom = useRef<HTMLDivElement>(null);
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const fetchRecruits2 = useCallback(
    async (pageParam: number) => {
      const res = await fetch(`${apiAddress}?page=${pageParam}&size=9`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          keywords: keywords,
          query: query,
        }),
      });
      return res.json();
    },
    [keywords, query]
  );

  const fetchRecruits = useCallback(async (pageParam: number) => {
    const res = await fetch(`${apiAddress}?page=${pageParam}&size=9&sort=null`);
    return res.json();
  }, []);

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
  });

  const imageErrorHandler = (index: number) => {
    const tmpWrongSrcs = [...wrongSrcs];
    tmpWrongSrcs[index] = true;
    setWrongSrcs(tmpWrongSrcs);
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
      <div className="flex flex-wrap justify-center ">
        {data?.pages.map((page, i: number) =>
          page.response?.content.map((recruit: Recruit, recruitI: number) => {
            console.log(recruit);
            return (
              <button
                type="button"
                onClick={() => recruitClickHandler(recruit.url)}
                key={recruitI}
                className={`${isMobile ? "w-full" : "w-[30%] mx-4 max-w-72"} my-4 h-[350px] rounded-xl overflow-hidden flex flex-col shadow `}
              >
                <Image
                  src={wrongSrcs[recruitI] ? baseImg : recruit.thumbnailUrl}
                  alt="thumbnail"
                  width="400"
                  height="400"
                  className={`shadow-inner shadow-black object-cover h-[50%] w-full`}
                  onError={() => imageErrorHandler(recruitI)}
                />
                <div className="flex flex-row justify-between w-full">
                  <div className="m-1 text-sm text-f5gray-500 ">
                    {recruit.company}
                  </div>
                  <div className="m-1 text-sm text-f5gray-500">
                    {"~" +
                      recruit.dueDate[0] +
                      "-" +
                      recruit.dueDate[1] +
                      "-" +
                      recruit.dueDate[2]}
                  </div>
                </div>
                <p className="text-f5black-300 font-bold h-12 text-left px-2">
                  {recruit.title}
                </p>
                <div className="ml-2 gap-2 flex flex-wrap">
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
                            className="h-10 w-auto"
                          />
                        </div>
                      );
                  })}
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
