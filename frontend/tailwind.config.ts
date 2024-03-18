import type { Config } from "tailwindcss";

const colors = require("tailwindcss/colors");

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    screens: {
      'labtop' : '700px',

    },
    extend: {
      colors: {
        f5green : {
          100: "#e6faf2",
          150: "#d9f8eb",
          200: "#b0f0d6",
          300: "#00ce7c",
          350: "#00b970",
          400: "#00a563",
          500: "#009b5d",
          550: "#007c4a",
          600: "#005d38",
          700: "#00482b",
        },
        f5yellowgreen: {
          200: "#d7ffa4",
          300: "#c1ff72"
        },
        f5red : {
          100: "#ffecec",
          150: "#ffe3e3",
          200: "#ffc4c4",
          300: "#ff4242",
          350: "#e63b3b",
          400: "#cc3535",
          500: "#bf3232",
          550: "#992828",
          600: "#731e1e",
          700: "#591717",
        },
        f5black : {
          400 : "#424242",
          500 : "#383838",
          600 : "#171717"
        }, 
        f5gray : {
          300 : "#E8E8E8",
          400 : "#D9D9D9",
          500 : "#848484",
        }
      },
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
      },
      keyframes: {
        startGauge: {
          '0%': {
            width: '0%',
          },
          '100%': {
            width: '100%',
          },
        },
      },
      animation: {
        'startGauge': 'startGauge 10s forwards linear',
      },
    },
  },
  plugins: [],
};
export default config;
