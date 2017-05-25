;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-shezhi" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M511.994372 697.339172c-101.688057 0-184.408356-82.862219-184.408356-184.710862 0-101.847619 82.720299-184.701652 184.408356-184.701652s184.417566 82.85301 184.417566 184.701652C696.411938 614.477976 613.682428 697.339172 511.994372 697.339172zM511.994372 388.822586c-68.157032 0-123.61022 55.543014-123.61022 123.805724 0 68.272943 55.453188 123.815957 123.61022 123.815957 68.166242 0 123.620454-55.543014 123.620454-123.815957C635.614825 444.3656 580.160614 388.822586 511.994372 388.822586z"  ></path>' +
    '' +
    '<path d="M581.629118 126.045741l0 40.661072c0 29.854958 17.801141 56.518225 45.331252 67.944462 8.955319 3.720741 18.401846 5.598508 28.096023 5.598508 19.615537 0 38.055247-7.650237 51.915474-21.538549l26.02579-26.061562 98.469627 98.629321-26.016579 26.061562c-21.074831 21.109784-27.321348 52.570309-15.913063 80.145341 11.399075 27.584242 38.026593 45.402049 67.827514 45.402049l39.339549 0 0 139.490961-39.339549 0c-29.800921 0-56.429462 17.817808-67.827514 45.39284-11.408285 27.584242-5.161768 59.043744 15.913063 80.153528l26.016579 26.062586-98.469627 98.629321-26.02579-26.062586c-13.86125-13.887288-32.299937-21.538549-51.915474-21.538549-9.694177 0-19.140703 1.87879-28.096023 5.599532-27.530111 11.416004-45.331252 38.089503-45.331252 67.943438l0 39.393195L442.369858 897.952212l0-39.393195c0-29.854958-17.791931-56.527434-45.322042-67.943438-8.955319-3.720741-18.41208-5.599532-28.106256-5.599532-19.605303 0-38.054223 7.65126-51.91445 21.538549l-26.026813 26.062586-98.469627-98.629321 26.017603-26.062586c21.074831-21.109784 27.320325-52.569286 15.913063-80.144318-11.399075-27.584242-38.026593-45.402049-67.828537-45.402049l-39.331362 0L127.301437 442.887946l39.331362 0c29.801944 0 56.430486-17.817808 67.828537-45.402049 11.407262-27.575032 5.161768-59.034535-15.913063-80.145341l-26.017603-26.061562 98.469627-98.629321 26.026813 26.061562c13.86125 13.888312 32.309147 21.538549 51.91445 21.538549 9.694177 0 19.141727-1.877767 28.096023-5.598508 27.540345-11.426237 45.332275-38.089503 45.332275-67.944462l0-40.661072L581.629118 126.045741M581.629118 65.150836 442.369858 65.150836c-33.44097 0-60.798136 31.204699-60.798136 64.697511l0 36.858466c0 5.124717-3.082325 9.729595-7.796889 11.69025-1.558559 0.64673-3.20001 0.957815-4.833273 0.957815-3.282901 0-6.510541-1.277086-8.927689-3.702322l-26.026813-26.061562c-11.818648-11.845793-27.402193-17.763572-42.987785-17.763572s-31.159927 5.91778-42.987785 17.763572l-98.477814 98.629321c-23.637295 23.681352-23.637295 62.445214 0 86.11838l26.026813 26.061562c3.610372 3.619434 4.686934 9.063423 2.73541 13.788028-1.951525 4.722558-6.556591 7.80578-11.663099 7.80578l-36.804715 0c-33.430737 0-63.323759 27.40107-63.323759 60.894905l0 139.490961c0 33.492812 29.893022 60.894905 63.323759 60.894905l36.804715 0c5.106507 0 9.711574 3.082198 11.663099 7.80578s0.874963 10.168594-2.73541 13.788028l-26.026813 26.062586c-23.637295 23.681352-23.637295 62.436004 0 86.117356l98.477814 98.629321c11.827858 11.835559 27.402193 17.763572 42.987785 17.763572s31.169137-5.928013 42.987785-17.763572l26.026813-26.061562c2.416125-2.416026 5.644788-3.702322 8.927689-3.702322 1.633264 0 3.273691 0.310062 4.833273 0.957815 4.714565 1.960655 7.796889 6.574742 7.796889 11.69025l0 36.859489c0 33.484625 27.357165 63.429635 60.798136 63.429635l139.25926 0c33.439947 0 60.797112-29.946033 60.797112-63.429635l0-36.859489c0-5.115508 3.073114-9.729595 7.796889-11.69025 1.558559-0.647753 3.201033-0.957815 4.833273-0.957815 3.282901 0 6.510541 1.286296 8.927689 3.702322l26.017603 26.061562c11.827858 11.835559 27.412426 17.763572 42.996995 17.763572 15.576382 0 31.170161-5.928013 42.988808-17.763572l98.477814-98.629321c23.637295-23.682375 23.637295-62.437028 0-86.117356l-26.026813-26.062586c-3.611396-3.619434-4.686934-9.063423-2.736433-13.788028 1.951525-4.723581 6.556591-7.80578 11.663099-7.80578l36.805739 0c33.430737 0 63.323759-27.40107 63.323759-60.894905L957.494652 442.887946c0-33.492812-29.893022-60.894905-63.323759-60.894905l-36.805739 0c-5.106507 0-9.711574-3.083222-11.663099-7.80578-1.950502-4.723581-0.874963-10.168594 2.736433-13.788028l26.026813-26.061562c23.637295-23.672142 23.637295-62.437028 0-86.11838l-98.477814-98.629321c-11.818648-11.845793-27.412426-17.763572-42.988808-17.763572-15.584569 0-31.169137 5.91778-42.996995 17.763572l-26.017603 26.061562c-2.416125 2.426259-5.644788 3.702322-8.927689 3.702322-1.63224 0-3.273691-0.310062-4.833273-0.957815-4.723775-1.960655-7.796889-6.565533-7.796889-11.69025l0-36.858466C642.42623 96.354512 615.069065 65.150836 581.629118 65.150836L581.629118 65.150836z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '<symbol id="icon-mianbaoxiedaohangxiaoxian" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M972.8 460.8 51.2 460.8C22.4 460.8 0 483.2 0 512c0 28.8 22.4 51.2 51.2 51.2l921.6 0c28.8 0 51.2-22.4 51.2-51.2C1024 483.2 1001.6 460.8 972.8 460.8z"  ></path>' +
    '' +
    '<path d="M51.2 153.6l921.6 0c28.8 0 51.2-22.4 51.2-51.2 0-28.8-22.4-51.2-51.2-51.2L51.2 51.2C22.4 51.2 0 73.6 0 102.4 0 131.2 22.4 153.6 51.2 153.6z"  ></path>' +
    '' +
    '<path d="M972.8 870.4 51.2 870.4c-28.8 0-51.2 22.4-51.2 51.2 0 28.8 22.4 51.2 51.2 51.2l921.6 0c28.8 0 51.2-22.4 51.2-51.2C1024 892.8 1001.6 870.4 972.8 870.4z"  ></path>' +
    '' +
    '</symbol>' +
    '' +
    '</svg>'
  var script = function() {
    var scripts = document.getElementsByTagName('script')
    return scripts[scripts.length - 1]
  }()
  var shouldInjectCss = script.getAttribute("data-injectcss")

  /**
   * document ready
   */
  var ready = function(fn) {
    if (document.addEventListener) {
      if (~["complete", "loaded", "interactive"].indexOf(document.readyState)) {
        setTimeout(fn, 0)
      } else {
        var loadFn = function() {
          document.removeEventListener("DOMContentLoaded", loadFn, false)
          fn()
        }
        document.addEventListener("DOMContentLoaded", loadFn, false)
      }
    } else if (document.attachEvent) {
      IEContentLoaded(window, fn)
    }

    function IEContentLoaded(w, fn) {
      var d = w.document,
        done = false,
        // only fire once
        init = function() {
          if (!done) {
            done = true
            fn()
          }
        }
        // polling for no errors
      var polling = function() {
        try {
          // throws errors until after ondocumentready
          d.documentElement.doScroll('left')
        } catch (e) {
          setTimeout(polling, 50)
          return
        }
        // no errors, fire

        init()
      };

      polling()
        // trying to always fire before onload
      d.onreadystatechange = function() {
        if (d.readyState == 'complete') {
          d.onreadystatechange = null
          init()
        }
      }
    }
  }

  /**
   * Insert el before target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var before = function(el, target) {
    target.parentNode.insertBefore(el, target)
  }

  /**
   * Prepend el to target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var prepend = function(el, target) {
    if (target.firstChild) {
      before(el, target.firstChild)
    } else {
      target.appendChild(el)
    }
  }

  function appendSvg() {
    var div, svg

    div = document.createElement('div')
    div.innerHTML = svgSprite
    svgSprite = null
    svg = div.getElementsByTagName('svg')[0]
    if (svg) {
      svg.setAttribute('aria-hidden', 'true')
      svg.style.position = 'absolute'
      svg.style.width = 0
      svg.style.height = 0
      svg.style.overflow = 'hidden'
      prepend(svg, document.body)
    }
  }

  if (shouldInjectCss && !window.__iconfont__svg__cssinject__) {
    window.__iconfont__svg__cssinject__ = true
    try {
      document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>");
    } catch (e) {
      console && console.log(e)
    }
  }

  ready(appendSvg)


})(window)