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
  useCallback,
  useEffect,
  useLayoutEffect,
  useRef,
} from "react";
import useSearchStore, { searchState } from "@/store/searchStore";
import { Recruit } from "@/type/interface";

const apiAddress = "https://spring.pickITup.online/recruit/search";
const techDataValues = Array.from(techDataMap.values());
const recruitClickHandler = (url: string) => {
  window.open(url, "_blank");
};

export default function RecruitPage() {
  const keywords = useSearchStore((state: searchState) => state.keywords);
  const query = useSearchStore((state: searchState) => state.query);
  const bottom = useRef<HTMLDivElement>(null);

  const fetchRecruits = useCallback(
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
            return (
              <button
                type="button"
                onClick={() => recruitClickHandler(recruit.url)}
                key={recruitI}
                className="w-[30%] max-w-72 h-[400px] m-4 rounded-xl overflow-hidden flex flex-col shadow "
              >
                <Image
                  src={recruit.thumbnailUrl}
                  alt="thumbnail"
                  width="300"
                  height="300"
                  className="shadow-inner shadow-black object-cover h-[50%]"
                  onError={(error) => console.log(error)}
                />
                <p className="m-1 text-sm text-f5gray-500 text-left">
                  {recruit.company}
                </p>
                <p className="text-f5black-300 font-bold h-12 text-left px-2">
                  {recruit.title}
                </p>
                <div className="gap-2 flex flex-wrap">
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

      {/* <div>
      <button
        onClick={() => fetchNextPage()}
        disabled={!hasNextPage || isFetchingNextPage}
      >
        {isFetchingNextPage
          ? "Loading more..."
          : hasNextPage
            ? "Load More"
            : "Nothing more to load"}
      </button>
    </div> */}
      <div>{isFetching && !isFetchingNextPage ? "Fetching..." : null}</div>
      <div ref={bottom} />
    </>
  );
}
