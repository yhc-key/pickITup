"use client";
import { useEffect } from "react";

export default function Practice() {
  useEffect(() => {
    const essayListfetchData = async () => {
      try {
        const res: Response = await fetch(
          "https://spring.pickITup.online/self/main",
          {
            headers: {
              Authorization:
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiIsImV4cCI6MTcxMDgzODI0Mn0.hIHFh_B-VnyIw3nlRAgENNx8igdbI-TGNyNpP8SuFUc",
            },
          }
        );
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        const jsonData = await res.json();
        console.log(jsonData);
      } catch (error) {
        console.log(error);
      }
    };

    const essaySubListfetchData = async () => {
      try {
        const res: Response = await fetch(
          "https://spring.pickITup.online/self/main/1/sub"
        );
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        const jsonData = await res.json();
        console.log(jsonData);
      } catch (error) {
        console.log(error);
      }
    };

    const loginFetchData = async () => {
      try {
        const res: Response = await fetch(
          "https://spring.pickITup.online/auth/login",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              username: "hscho",
              password: "1234",
            }),
          }
        );
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        const jsonData = await res.json();
        console.log(jsonData);
      } catch (error) {
        console.error(error);
      }
    };

    essayListfetchData();
    essaySubListfetchData();
    // loginFetchData();
  }, []);
  return <div> 먼가 연습할거임</div>;
}
